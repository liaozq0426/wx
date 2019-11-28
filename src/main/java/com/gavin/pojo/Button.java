package com.gavin.pojo;

/**
 * @author gavin
 * @title 微信按钮
 */
public class Button{
	
	public static final String TYPE_VIEW = "view";	// view类型按钮
	public static final String TYPE_CLICK = "click";// click类型按钮

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
