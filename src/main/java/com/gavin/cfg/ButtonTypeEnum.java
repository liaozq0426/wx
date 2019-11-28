package com.gavin.cfg;

public enum ButtonTypeEnum {
	
	VIEW("view")
	,CLICK("click")
	;
	private String type;
	
	
	
	
	
	public String getType() {
		return type;
	}





	public void setType(String type) {
		this.type = type;
	}





	ButtonTypeEnum(String type) {
		this.type = type;
	}
}
