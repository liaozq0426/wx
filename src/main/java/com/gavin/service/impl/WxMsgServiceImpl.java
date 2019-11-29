package com.gavin.service.impl;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gavin.pojo.TextMessage;
import com.gavin.pojo.WxMessageConst;
import com.gavin.service.WxMsgService;
import com.gavin.util.BeanXmlUtil;


/**
 * @title 微信消息处理
 * @author gavin
 * @date 2019年11月29日
 */
@Service
public class WxMsgServiceImpl implements WxMsgService{
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public String processWxRequest(HttpServletRequest request) throws Exception {
		// xml请求解析成map
		Map<String, String> requestMap = BeanXmlUtil.xmlToMap(request);

		logger.info(requestMap.toString());
		// 发送方帐号
		String fromUserName = requestMap.get(WxMessageConst.FROM_USER_NAME);

		// 接受方帐号
		String toUserName = requestMap.get(WxMessageConst.TO_USER_NAME);

		// 消息类型
		String msgType = requestMap.get(WxMessageConst.MSG_TYPE);

		if (WxMessageConst.MSG_TYPE_EVENT.equals(msgType)) {
			// 事件消息类型
			
			// 订阅
			if (WxMessageConst.EVENT_TYPE_SUBSCRIBE.equals(requestMap.get(WxMessageConst.EVENT))) {
				// 关注时自动回复消息
				TextMessage textMessage = new TextMessage();
				textMessage.setContent("感谢您关注，我会继续努力！");
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setFromUserName(toUserName);
				textMessage.setToUserName(fromUserName);
				textMessage.setMsgType(WxMessageConst.MSG_TYPE_TEXT);
				String response = BeanXmlUtil.beanToXmlCommon(textMessage);
        		if(response != null)
        			return response;
				
			} else if (WxMessageConst.EVENT_TYPE_UN_SUBSCRIBE.equals(requestMap.get(WxMessageConst.EVENT))) {
				// 取消订阅
				logger.info("用户" + fromUserName + "取消关注了");
			}
			
		}else if(WxMessageConst.MSG_TYPE_TEXT.equals(msgType)) {
			// 文本消息类型
			// 定义自动回复文本消息
			TextMessage textMessage = new TextMessage();
			if (!StringUtils.isBlank(msgType)) {
				textMessage.setFromUserName(toUserName);
				textMessage.setToUserName(fromUserName);
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setMsgType(WxMessageConst.MSG_TYPE_TEXT);
				// 用户发送过来的消息
				String content = requestMap.get(WxMessageConst.CONTENT);
				logger.info(content);
				
				// 回复消息
				String rspContent = "您好，有什么问题可以问我！";
	        	textMessage.setContent(rspContent);
				
	        	if(!StringUtils.isBlank(textMessage.getContent())) {	        		
	        		String response = BeanXmlUtil.beanToXmlCommon(textMessage);
	        		logger.info(response);
	        		if(response != null)
	        			return response;
	        	}
			}
		}else {
			// 其他消息
		}
		// 默认回复
		return WxMessageConst.DEFAULT_MSG;
	}
}
