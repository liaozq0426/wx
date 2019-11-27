package com.gavin.pojo;

import java.util.Date;

public class WxToken {
	
	private Integer id;
	
	private String tokenType;
	
	private String accessToken;
	
	private Integer expiresIn;	// 有效时间[秒]
	
	private Date createTime;
	
	private Date lastUpdTime;	
	
	private Integer refreshCount;	// 刷新次数
	
	private String platform;	// 所属平台
	
	

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdTime() {
		return lastUpdTime;
	}

	public void setLastUpdTime(Date lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
	}

	public Integer getRefreshCount() {
		return refreshCount;
	}

	public void setRefreshCount(Integer refreshCount) {
		this.refreshCount = refreshCount;
	}
	
	
	
	
}
