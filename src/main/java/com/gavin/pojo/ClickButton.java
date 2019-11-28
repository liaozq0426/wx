package com.gavin.pojo;

/**
 * @title 微信click类型按钮
 * @author gavin
 * @date 2019年11月28日
 */
public class ClickButton extends Button{
	
	private String type;
	
	private String key;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}	
}

