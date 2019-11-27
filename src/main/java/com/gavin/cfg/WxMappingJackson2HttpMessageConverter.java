package com.gavin.cfg;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

	
/**
 * @author Administrator
 * @title 添加微信返回text/plain转换为json格式处理
 */
public class WxMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    public WxMappingJackson2HttpMessageConverter(){
        List<MediaType> mediaType = new ArrayList<>();
        mediaType.add(MediaType.TEXT_HTML);
        mediaType.add(MediaType.TEXT_PLAIN);
        mediaType.add(MediaType.APPLICATION_OCTET_STREAM);
        setSupportedMediaTypes(mediaType);
    }

}
