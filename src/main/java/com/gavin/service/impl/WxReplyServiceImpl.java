package com.gavin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gavin.mapper.WxReplyMapper;
import com.gavin.pojo.WxReply;
import com.gavin.service.WxReplyService;

@Service
public class WxReplyServiceImpl implements WxReplyService{
	
	@Autowired
	private WxReplyMapper wxReplyMapper;

	@Override
	public int insert(WxReply wxReply) throws Exception {
		return wxReplyMapper.insert(wxReply);
	}
}
