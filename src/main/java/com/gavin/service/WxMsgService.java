package com.gavin.service;

import javax.servlet.http.HttpServletRequest;

public interface WxMsgService {

	String processWxRequest(HttpServletRequest request) throws Exception;
}
