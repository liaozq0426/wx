package com.gavin.util;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.gavin.pojo.AccessToken;
import com.gavin.pojo.WxApiCfg;
import com.google.gson.Gson;

@Component
@DependsOn(value="restTemplate")
public class WxUtil {
	
	
	public static RestTemplate restTemplate;
	
	
    
    @Autowired
    public void setRestTemplate(@Qualifier("restTemplate") RestTemplate restTemplate) throws Exception{
        WxUtil.restTemplate = restTemplate;
    }
	
	/**
     * @title 微信公众号服务器配置校验
     * @param signatureWx
     * @param token
     * @param nonce
     * @param timestamp
     * @return
     */
    public static boolean verifySignature(String signatureWx , String token , String nonce , String timestamp) throws Exception{
        if(StringUtils.isBlank(signatureWx))
            return false;
        String[] arr = {token , nonce , timestamp};
        StringBuffer sb = new StringBuffer();
        // 排序
        Arrays.sort(arr);
        // 拼接字符串
        sb.append(arr[0]).append(arr[1]).append(arr[2]);
        // sha1算法加密
        String signature = DigestUtils.sha1Hex(sb.toString());
        return signatureWx.equals(signature);
    }
    
    /**
     * @title 获取微信公众号全局唯一凭证
     * @param appId
     * @param secret
     * @return
     */
    public static AccessToken getAccessToken(String appId , String secret) throws Exception{

        String accessTokenUrl = WxApiCfg.accessTokenUrl
                .replace(WxApiCfg.FORM_PARAM_APPID, appId)
                .replace(WxApiCfg.FORM_PARAM_APPSECRET, secret);
		@SuppressWarnings("unchecked")
		Map<String , Object> map = restTemplate.getForObject(accessTokenUrl, Map.class);
		Gson gson = new Gson();
		System.out.println("AccessToken:" + gson.toJson(map));
		return gson.fromJson(gson.toJson(map), AccessToken.class);
    }
    
    /**
     * @title 获取js_ticket
     * @param accessToken 基础凭证
     * @return
     */
    public static AccessToken getJSTicket(String accessToken) throws Exception{
        String jsTicketUrl = WxApiCfg.apiTicketUrl
                .replace(WxApiCfg.FORM_PARAM_ACCESS_TOKEN, accessToken);
        AccessToken token = restTemplate.getForObject(jsTicketUrl, AccessToken.class);
        return token;
    }
}
