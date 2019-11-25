package com.gavin.util;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public class WxUtil {
	/**
     * @title 微信公众号服务器配置校验
     * @param signatureWx
     * @param token
     * @param nonce
     * @param timestamp
     * @return
     */
    public static boolean verifySignature(String signatureWx , String token , String nonce , String timestamp) throws Exception{
        if(StringUtils.isBlank(signatureWx))
            return false;
        String[] arr = {token , nonce , timestamp};
        StringBuffer sb = new StringBuffer();
        // 排序
        Arrays.sort(arr);
        // 拼接字符串
        sb.append(arr[0]).append(arr[1]).append(arr[2]);
        // sha1算法加密
        String signature = DigestUtils.sha1Hex(sb.toString());
        return signatureWx.equals(signature);
    }
}
