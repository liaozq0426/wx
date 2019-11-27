package com.gavin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gavin.pojo.AccessToken;
import com.gavin.pojo.WxAccessVo;
import com.gavin.service.WxCfgService;
import com.gavin.service.WxTokenService;

@RestController
public class WxController {
	
	@Autowired
	private WxCfgService wxCfgService;
	
	@Autowired
	private WxTokenService wxTokenService;
	
	@GetMapping("wxIn")
	public String wxIn(WxAccessVo accessVo) {
		try {
			return wxCfgService.wxInVerify(accessVo); 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	@GetMapping("wx/token")
	public Object getWxToken(String accessType , String platform) {
		try {
			return this.wxTokenService.readAccessToken(accessType, "gavin");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
