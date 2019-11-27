package com.gavin.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Administrator
 * @title 加密工具类
 */
public class EncryptionUtil {
	
	// Base64加密字符串
	public static String base64Encode(String data) {
		if(data != null)
			return Base64.encodeBase64String(data.getBytes());
		return null;
	}
	
	// Base64加密字节数组
	public static String base64Encode(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}
	
	// Base64解密 返回字符串
	public static String base64Decode(String data) {
		
		return new String(Base64.decodeBase64(data));
	}
	
	// Base64解密 返回字节数组
	public static byte[] base64Decode2(String data) {
		return Base64.decodeBase64(data);
	}
	
	// md5加密
	public static String md5(String data) {
		return DigestUtils.md5Hex(data);
	}
	
	/**
	 * @title md5加盐加密
	 * @param data
	 * @param salt
	 * @return
	 */
	public static String md5(String data , String salt) {
		return md5(salt + data);
	}
	
	// sha1加密
	public static String sha1(String data) {
		return DigestUtils.sha1Hex(data);
	}
	
	// sha256加密
	public static String sha256Hex(String data) {
		return DigestUtils.sha256Hex(data);
	}

}
