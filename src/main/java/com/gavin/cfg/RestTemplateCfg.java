package com.gavin.cfg;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;



@Configuration
public class RestTemplateCfg {
    @Bean("restTemplate")
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        RestTemplate restTemplate = new RestTemplate(factory);

        // 使用utf-8编码集的convert替换默认的convert(ISO-8859-1)
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
        while(iterator.hasNext()) {
            HttpMessageConverter<?> converter = iterator.next();
            if(converter instanceof StringHttpMessageConverter)
                iterator.remove();
        }
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        // 添加WxMappingJackson2HttpMessageConverter支持
        messageConverters.add(new WxMappingJackson2HttpMessageConverter());
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(15000);	// ms
        factory.setConnectTimeout(15000);	// ms
        return factory;
    }
}
