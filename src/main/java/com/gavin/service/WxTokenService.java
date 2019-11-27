package com.gavin.service;

import java.util.List;

import com.gavin.pojo.AccessToken;
import com.gavin.pojo.WxToken;
public interface WxTokenService {
	public List<WxToken> select(WxToken token) throws Exception;
	
	public WxToken selectOne(WxToken token) throws Exception;
	
	public int save(WxToken token) throws Exception;
	
	public AccessToken readAccessToken(String accessType , String platform) throws Exception;
}
