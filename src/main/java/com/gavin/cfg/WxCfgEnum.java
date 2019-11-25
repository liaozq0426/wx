package com.gavin.cfg;

public enum WxCfgEnum {
	APP_URL("app_url" , "appUrl")
	,WX_TOKEN("wx_token" , "wxToken")
	,WX_KEY_APPID("wx_key" , "appId")
	,WX_KEY_APPSECRET("wx_key" , "appSecret")
	,MENU("menu")
	;
	private String type;	// 对应wx_cfg表中的type字段
	private String name;	// 对应wx_cfg表中的name字段
	private WxCfgEnum(String type , String name) {
		this.type = type;
		this.name = name;
	}
	private WxCfgEnum(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
