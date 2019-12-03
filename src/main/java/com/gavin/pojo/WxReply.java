package com.gavin.pojo;

import java.util.Date;

/**
 * @title 消息记录
 * @author gavin
 * @date 2019年12月2日
 */
public class WxReply {
	
	private Integer id;		// 自增主键
	
	private String openid;	// 用户标识
	
	private String request;	// 请求内容
	
	private String response;	// 响应内容
	
	private Date createTime;	// 创建时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
