package com.gavin.mapper;

import java.util.List;

import com.gavin.pojo.WxToken;

public interface WxTokenMapper {
	List<WxToken> select(WxToken token);
	int insert(WxToken token);
	int update(WxToken token);
}
