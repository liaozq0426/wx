package com.gavin.pojo;

/**
 * @author gavin
 * @title 微信复合按钮
 */
public class ComplexButton extends Button{
	
	private Button[] sub_button;

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
}
