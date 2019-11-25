package com.gavin.service;

import java.util.List;

import com.gavin.cfg.WxAccessVo;
import com.gavin.pojo.Wechat;
import com.gavin.pojo.WxCfg;

public interface WxCfgService {
	List<WxCfg> select(WxCfg cfg) throws Exception;
	WxCfg selectOne(WxCfg cfg) throws Exception;
	Wechat selectWechat(String platform) throws Exception;
	String wxInVerify(WxAccessVo accessVo) throws Exception;
}
