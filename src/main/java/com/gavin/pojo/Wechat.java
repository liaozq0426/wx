package com.gavin.pojo;

import com.gavin.util.EncryptionUtil;

public class Wechat {
	
	private String appId;
	
	private String appSecret;
	
	
	public String getBase64DecodeAppId() {
		return EncryptionUtil.base64Decode(appId);
	}
	
	public String getBase64DecodeAppSecret() {
		return EncryptionUtil.base64Decode(appSecret);
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
}
