package com.gavin.pojo;

/**
 * @title 微信view类型按钮
 * @author gavin
 * @date 2019年11月28日
 */
public class ViewButton extends Button{
	
	private String type;
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
