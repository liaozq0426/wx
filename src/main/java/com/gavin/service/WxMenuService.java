package com.gavin.service;

import com.gavin.pojo.Menu;

public interface WxMenuService {
	public Menu makeMenu(String platform) throws Exception;
}
