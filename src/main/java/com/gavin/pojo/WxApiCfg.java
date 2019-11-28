package com.gavin.pojo;

public class WxApiCfg {
	public static final String FORM_PARAM_APPID = "APPID";
    public static final String FORM_PARAM_APPSECRET = "APPSECRET";
    public static final String FORM_PARAM_SECRET = "SECRET";
    public static final String FORM_PARAM_REDIRECT_URI = "REDIRECT_URI";
    public static final String FORM_PARAM_SCOPE = "SCOPE";
    public static final String FORM_PARAM_STATE = "STATE";
    public static final String FORM_PARAM_CODE = "CODE";
    public static final String FORM_PARAM_ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String FORM_PARAM_OPENID = "OPENID";

    // 网页授权scope
    public static final String SCOPE_BASE = "snsapi_base";
    // 获取用户的基本信息
    public static final String SCOPE_USERINFO = "snsapi_userinfo";



    // 全局access_token
    public static String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    // 获取code
    public static String codeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

    // 网页access_token
    public static String webpageAccessTokenUrl =  "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    // 拉取用户信息[获取code时SCOPE必须为snsapi_userinfo]
    public static String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    // js api ticket
    public static String apiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    // 创建自定义菜单
    public static String createMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
}
