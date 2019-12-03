package com.gavin.controller;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gavin.pojo.Menu;
import com.gavin.pojo.WxAccessVo;
import com.gavin.service.WxCfgService;
import com.gavin.service.WxMenuService;
import com.gavin.service.WxMsgService;
import com.gavin.service.WxTokenService;

@RestController
public class WxController {
	
	@Autowired
	private WxCfgService wxCfgService;
	
	@Autowired
	private WxTokenService wxTokenService;
	
	@Autowired
	private WxMenuService wxMenuService;
	
	@Autowired
	private WxMsgService wxMsgService;
	
	@GetMapping("wxIn")
	public String wxIn(WxAccessVo accessVo) {
		try {
			return wxCfgService.wxInVerify(accessVo); 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	
	@PostMapping("wxIn")
    public void wxMsgReply(HttpServletRequest request, HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setCharacterEncoding("UTF-8");
        try {
            // String respMsg = "success";
        	String respMsg = wxMsgService.processWxRequest(request);
            PrintWriter out = response.getWriter();
            out.print(respMsg);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
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
	
	@GetMapping("wx/menu")
	public Menu getWxMenu(String platform) {
		try {
			return this.wxMenuService.makeMenu(platform);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
}
