package com.gavin.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gavin.cfg.WxCfgEnum;
import com.gavin.mapper.WxCfgMapper;
import com.gavin.pojo.Wechat;
import com.gavin.pojo.WxAccessVo;
import com.gavin.pojo.WxCfg;
import com.gavin.service.WxCfgService;
import com.gavin.util.EncryptionUtil;
import com.gavin.util.WxUtil;

@Service
public class WxCfgServiceImpl implements WxCfgService{ 
	
	@Autowired
	private WxCfgMapper wxCfgMapper;

	/**
	 * @title 查询WxCfg集合
	 * @author gavin
	 * @date 2019年11月25日
	 */
	@Override
	public List<WxCfg> select(WxCfg cfg) throws Exception {
		return wxCfgMapper.select(cfg);
	}
	/**
	 * @title 查询WxCfg单条记录
	 * @author gavin
	 * @date 2019年11月25日
	 */
	@Override
	public WxCfg selectOne(WxCfg cfg) throws Exception {
		List<WxCfg> cfgList = select(cfg);
		if(cfgList.size() == 1)
			return cfgList.get(0);
		return null;
	}
	/**
	 * @title 查询微信公众号appId和appSecret
	 * @author gavin
	 * @date 2019年5月24日
	 * @param platform
	 * @return
	 * @throws Exception
	 */
	@Override
	public Wechat selectWechat(String platform) throws Exception {
		WxCfg cfgParam = new WxCfg();
		cfgParam.setPlatform(platform);
		cfgParam.setType(WxCfgEnum.WX_KEY_APPID.getType());
		List<WxCfg> cfgList = wxCfgMapper.select(cfgParam);
		
		Wechat wechat = new Wechat();
		for(WxCfg cfg : cfgList) {
			String name = cfg.getName();
			if(WxCfgEnum.WX_KEY_APPID.getName().equals(name)) {
				// 获取appId
				String appId = cfg.getValue();
				wechat.setAppId(appId);
			}else if(WxCfgEnum.WX_KEY_APPSECRET.getName().equals(name)) {
				// 获取appSecret
				String appSecret = cfg.getValue();
				wechat.setAppSecret(appSecret);
			}
		}
		return wechat;
	}
	/**
	 * @title 微信公众号接入校验
	 * @author gavin
	 * @date 2019年11月25日
	 */
	@Override
	public String wxInVerify(WxAccessVo accessVo) throws Exception{
		 boolean result = false;
		if(accessVo == null)
			return null;
		String platform = accessVo.getPlatform();
		if (!StringUtils.isBlank(platform)) {
			// 从数据库中查询Token
            WxCfg wxCfg = new WxCfg();
            wxCfg.setEnabled(true);
            wxCfg.setType(WxCfgEnum.WX_TOKEN.getType());
            wxCfg.setPlatform(platform);
            WxCfg wxCfgA = this.selectOne(wxCfg);
            String token = wxCfgA.getValue();
            token = EncryptionUtil.base64Decode(token);
            // 校验签名
            result = WxUtil.verifySignature(accessVo.getSignature(), token, accessVo.getNonce(), accessVo.getTimestamp());
        }
		System.out.println(accessVo.getPlatform() + "配置微信公众号" + (result ? "成功" : "失败"));
		// 如果校验成功则返回echostr，否则返回空
		return result ? accessVo.getEchostr() : null;
	}
}
