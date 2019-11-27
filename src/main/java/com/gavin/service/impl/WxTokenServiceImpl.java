package com.gavin.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gavin.cfg.RedisService;
import com.gavin.mapper.WxTokenMapper;
import com.gavin.pojo.AccessToken;
import com.gavin.pojo.Wechat;
import com.gavin.pojo.WxToken;
import com.gavin.service.WxCfgService;
import com.gavin.service.WxTokenService;
import com.gavin.util.WxUtil;

@Service
public class WxTokenServiceImpl implements WxTokenService , DisposableBean {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	public static final int DEFAULT_ACCESS_TOKEN_EXPIRESIN = 120;
	
	public static Map<String , AtomicInteger> tokenSyncMap = new ConcurrentHashMap<>();
	
	@Autowired
	private WxTokenMapper wxTokenMapper;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private WxCfgService wxCfgService;

	/**
	 * @title 查询token集合
	 * @author gavin
	 * @date 2019年11月27日
	 */
	@Override
	public List<WxToken> select(WxToken token) throws Exception {
		return wxTokenMapper.select(token);
	}

	/**
	 * @title 查询单个token
	 * @author gavin
	 * @date 2019年11月27日
	 */
	@Override
	public WxToken selectOne(WxToken token) throws Exception {
		List<WxToken> tokenList = select(token);
		if(tokenList.size() == 1)
			return tokenList.get(0);
		logger.info("查询结果集不符合预期");
		return null;
	}

	/**
	 * @title 保存token至数据库
	 * @author gavin
	 * @date 2019年11月27日
	 */
	@Override
	public int save(WxToken token) throws Exception {
		Integer id = token.getId();
		if(id != null && id > 0) {
			// 更新
			return this.wxTokenMapper.update(token);
		}else {
			// 新增
			return this.wxTokenMapper.insert(token);
		}
	}

	/**
	 * @title 读取微信token
	 * @author gavin
	 * @date 2019年11月27日
	 */
	@Override
	public AccessToken readAccessToken(String accessType, String platform) throws Exception {
		// 1.尝试从redis中读取
		AccessToken token = null;
		try {
			token = readAccessTokenByRedisAndDb(accessType , platform);
			if(token != null && !StringUtils.isBlank(token.getAccess_token()))
				return token;
			
			if(tokenSyncMap.get(accessType) != null && tokenSyncMap.get(accessType).get() > 0) {
				while(tokenSyncMap.get(accessType).get() > 0) {
					// 此时正在向微信服务器请求token，阻塞等待
					Thread.sleep(100);
					logger.info("正在向微信服务器请求token，阻塞等待...");
				}
				token = readAccessTokenByRedisAndDb(accessType , platform);
				if(token != null && !StringUtils.isBlank(token.getAccess_token()))
					return token;
				else
					return null;
			}else {					
				// 3.尝试从微信服务器上获取
				// 同步intern，保证在同一时间段内仅访问远程服务器一次
				String intern = accessType.intern();
				synchronized (intern) {					
					tokenSyncMap.put(accessType, new AtomicInteger(1));
					try {
						if(AccessToken.TYPE_ACCESS_TOKEN.equals(accessType) || AccessToken.TYPE_JSAPI_TOKEN.equals(accessType)) {			
							
							Wechat wechat = wxCfgService.selectWechat(platform);
							
							if(AccessToken.TYPE_ACCESS_TOKEN.equals(accessType)) {			
								logger.info("从微信服务器上获取access_token");
								token = WxUtil.getAccessToken(wechat.getBase64DecodeAppId(), wechat.getBase64DecodeAppSecret());
							}
							if(AccessToken.TYPE_JSAPI_TOKEN.equals(accessType)) {
								logger.info("从微信服务器上获取js_ticket");
								AccessToken Atoken = readAccessToken(AccessToken.TYPE_ACCESS_TOKEN , platform);
								token = WxUtil.getJSTicket(Atoken.getAccess_token());
							}
						}
						if(token != null && !StringUtils.isBlank(token.getAccess_token())) {				
							token.setAccess_type(accessType);
							// 缓存token
							cacheAccessToken(token , platform);
						}else {
							logger.error("从微信服务器上获取access_token失败");
						}
					} catch (Exception e) {
						logger.error("从微信服务器上获取access_token失败");
						logger.error(e.getMessage() , e);
					} finally {
						tokenSyncMap.get(accessType).decrementAndGet();
						logger.info("tokenSyncMap." + accessType + " count:" + tokenSyncMap.get(accessType).get());
					}
				}
				return token;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("读取access_token失败");
		}
	}
	
	
	/**
	 * @title 从redis和数据库中读取accessToken
	 * @param accessType
	 * @return
	 */
	private AccessToken readAccessTokenByRedisAndDb(String accessType , String platform) {
		if(StringUtils.isBlank(accessType)) return null;
		String redisKey = null;
		if(!StringUtils.isBlank(accessType)) {
			// 生产redisKey
			redisKey = makeAccessTokenRedisKey(accessType , platform);
		}
		AccessToken token = null;
		try {
			Object obj = null;
			// 1.尝试从redis中读取
			logger.info("尝试从redis中读取...");
			obj = redisService.get(redisKey);
			if(obj != null) 
				token = (AccessToken) obj;
			if(token != null && !StringUtils.isBlank(token.getAccess_token())) {
				long tokenCreateTime = token.getCreate_time();
				logger.info("tokenCreateTime:" + tokenCreateTime);
				long interval = (System.currentTimeMillis() - tokenCreateTime) / 1000;
				logger.info("interval:" + interval);
				if(interval <= (token.getExpires_in() - DEFAULT_ACCESS_TOKEN_EXPIRESIN)) {					
					return token;
				}else {
					logger.info("redis中的accessToken已经失效");
					redisService.del(redisKey);
				}
			}
		} catch (Exception e) {
			logger.error("尝试从redis中读取access_token失败");
			logger.error(e.getMessage() , e);
		}
		
		// 2.尝试从数据库中获取
		try {
			logger.info("尝试从数据库中读取...");
			WxToken wxTokenParam = new WxToken();
			wxTokenParam.setTokenType(accessType);
			wxTokenParam.setPlatform(platform);
			WxToken wxToken = this.selectOne(wxTokenParam);		
			if(wxToken != null) {
				// 判断token是否失效
				int expiresIn = wxToken.getExpiresIn();
				Date lastUpdTime = wxToken.getLastUpdTime();
				logger.info("System.currentTimeMillis:" + System.currentTimeMillis());
				logger.info("lastUpdTime:" + lastUpdTime.getTime() + ",format:" + lastUpdTime);
				long interval = (System.currentTimeMillis() - lastUpdTime.getTime()) / 1000;
				logger.info("interval:" + interval);
				if(interval <= (expiresIn - DEFAULT_ACCESS_TOKEN_EXPIRESIN)) {
					token = new AccessToken();
					token.setAccess_token(wxToken.getAccessToken());
					token.setAccess_type(wxToken.getTokenType());
					token.setExpires_in(wxToken.getExpiresIn());
					token.setCreate_time(wxToken.getLastUpdTime().getTime());
					// 同步至redis
					// long redisExpires = System.currentTimeMillis() - wxToken.getLastUpdTime().getTime();
					long redisExpires = expiresIn - interval;
					redisService.set(redisKey, token , redisExpires);
					return token;	
				}
			}
			
		} catch (Exception e) {
			logger.error("尝试从数据库中读取access_token失败");
			logger.error(e.getMessage() , e);
		}
		return null;
	}
	
	/**
	 * @title 缓存access token，1.缓存至redis 2.缓存至数据库
	 * @author gavin
	 * @date 2019年5月23日
	 * @param accessToken
	 * @param platform
	 * @throws Exception
	 */
	public void cacheAccessToken(AccessToken accessToken , String platform) throws Exception {
		// 如果token的创建时间为空，则必须设置（从微信服务器获取到token时create_time为空）
		if(accessToken.getCreate_time() == 0) {			
			accessToken.setCreate_time(new Date().getTime());
			logger.info("设置token创建时间");
		}
		logger.info("accessToken_createTime:" + accessToken.getCreate_time());
		logger.info("System.currentTimeMillis:" + System.currentTimeMillis());
		// 1.缓存至redis
		String redisKey = null;
		String accessType = accessToken.getAccess_type();
		if(!StringUtils.isBlank(accessType)) {			
			redisKey = makeAccessTokenRedisKey(accessType , platform);
			
			redisService.set(redisKey, accessToken, accessToken.getExpires_in());
			logger.info("缓存" + platform + " " + accessType + "至redis成功");
			// 2.缓存至数据库
			WxToken tokenParam = new WxToken();
			tokenParam.setTokenType(accessType);
			tokenParam.setAccessToken(accessToken.getAccess_token());
			tokenParam.setExpiresIn(accessToken.getExpires_in());
			tokenParam.setPlatform(platform);
			// 1.先查询数据库中是否存在记录
			int result = 0;
			WxToken wxAccessToken = this.selectOne(tokenParam);
			if(wxAccessToken == null) {
				// 首次插入
				tokenParam.setRefreshCount(0);
				result = this.wxTokenMapper.insert(tokenParam);
			}else {
				// 更新
				if(wxAccessToken.getRefreshCount() == null) {
					tokenParam.setRefreshCount(1);
				}else {					
					tokenParam.setRefreshCount(wxAccessToken.getRefreshCount() + 1);
				}
				tokenParam.setId(wxAccessToken.getId());
				result = this.wxTokenMapper.update(tokenParam);
			}
			if(result == 1) 
				logger.info("缓存" + platform + " " + tokenParam.getTokenType() + "至数据库成功");
			else
				logger.info("缓存" + platform + " " + tokenParam.getTokenType() + "至数据库失败");
		}
	}
	
	/**
	 * @title access_token redis 缓存 key规则
	 * @author gavin
	 * @date 2019年5月23日
	 * @param accessType
	 * @param platform
	 * @return
	 */
	private String makeAccessTokenRedisKey(String accessType , String platform) {
		String redisKey = platform + "_" + accessType;
		return redisKey;
	}

	/**
	 * @title 销毁时清空缓存
	 * @author gavin
	 * @date 2019年11月27日
	 */
	@Override
	public void destroy() throws Exception {
		tokenSyncMap.clear();
		System.out.println("tokenSyncMap清空了,size:" + tokenSyncMap.size());
	}

}
