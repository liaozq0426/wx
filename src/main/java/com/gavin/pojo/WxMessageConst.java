package com.gavin.pojo;

/**
 * @title 微信消息类型定义bean
 * @author gavin
 * @date 2019年11月29日
 */
public class WxMessageConst {
	
	public static String MSG_TYPE = "MsgType";	// 消息类型  包含text:文本消息、event:事件类型等
	
    public static String MSG_TYPE_TEXT = "text";  // 本文消息
    public static String MSG_TYPE_EVENT = "event";	// 事件消息
    
    public static String EVENT = "Event";	// 值可以为subscribe、unsubscribe等
    public static String EVENT_KEY = "EventKey"; // 事件KEY值，与自定义菜单接口中KEY值对应
    
   
    public static String EVENT_TYPE_SUBSCRIBE = "subscribe";  //订阅
    public static String EVENT_TYPE_UN_SUBSCRIBE = "unsubscribe";  //取消订阅
    public static String EVENT_TYPE_CLICK = "CLICK";	// 点击菜单
    
    public static String FROM_USER_NAME = "FromUserName";
    public static String TO_USER_NAME = "ToUserName";
    
    public static String CONTENT = "Content";	// 消息内容
    
    public static String DEFAULT_MSG = "success";	// 如果不返回自定义消息，则返回success
}
