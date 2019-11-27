package com.gavin.pojo;

import java.io.Serializable;

/**
 * @author gavin
 * @title 微信凭证对接
 */
public class AccessToken implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 基础 token类型
	public static final String TYPE_ACCESS_TOKEN = "access_token";
	// jsapi token类型
	public static final String TYPE_JSAPI_TOKEN = "jsapi_token";
	 
	// 对外公开凭证
	private String access_token;
	
	@SuppressWarnings("unused")
	private String ticket;
	
	// 凭证有效时间，单位：秒
	private int expires_in;
	
	// 记录凭证的创建时间
	private long create_time;
	
	// token类型，目前有基础access_token和js_tickt两种
	private String access_type;
	
	public AccessToken() {
		
	}
	public AccessToken(String access_type) {
		this.access_type = access_type;
	}
	

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		System.out.println("执行了");
		this.access_token = access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}

	public String getAccess_type() {
		return access_type;
	}

	public void setAccess_type(String access_type) {
		this.access_type = access_type;
	}

	

	public void setTicket(String ticket) {
		this.access_token = ticket;
	}
	
	
	
	
	
}
