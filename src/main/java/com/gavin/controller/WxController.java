package com.gavin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gavin.cfg.WxAccessVo;
import com.gavin.service.WxCfgService;

@RestController
public class WxController {
	
	@Autowired
	private WxCfgService wxCfgService;
	
	@GetMapping("wxIn")
	public String wxIn(WxAccessVo accessVo) {
		try {
			return wxCfgService.wxInVerify(accessVo); 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
}
