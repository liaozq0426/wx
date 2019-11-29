package com.gavin.pojo;

/**
 * @title 文本消息bean
 * @author gavin
 * @date 2019年11月29日
 */
public class TextMessage {

    private String ToUserName;		// 消息接收方
    private String FromUserName;	// 消息发送方
    private long CreateTime;	
    private String MsgType;		// 消息类型
    private String Content;		// 消息内容

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }


    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

}
