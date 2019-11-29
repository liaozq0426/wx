package com.gavin.util;

import com.thoughtworks.xstream.XStream;

/**
 * @title 自定义XStream
 * @author gavin
 * @date 2019年11月29日
 */
public class CommonXStream extends XStream {
	
	public CommonXStream(CommonXppDriver driver) {
		super(driver);
	}
	public CommonXStream(CommonXppDriver driver , Class<?> rootClassType) {
		super(driver);
		// 将根元素重命名为xml
		this.alias("xml", rootClassType);
	}
}
