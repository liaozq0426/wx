package com.gavin.util;

import java.io.Writer;

import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * @title 自定义XappDriver ， 对dom元素增加CDATA标记
 * @author gavin
 * @date 2019年11月29日
 */
public class CommonXppDriver extends XppDriver {
	
	public CommonXppDriver() {
		super();
	}
	
	public CommonXppDriver(NoNameCoder noNameCoder) {
		super(noNameCoder);
	}

	@Override
	public HierarchicalStreamWriter createWriter(Writer out) {
		return new PrettyPrintWriter(out) {
            // 对所有xml节点的转换都增加CDATA标记
            boolean cdata = true;
         
            @Override
            @SuppressWarnings("rawtypes")
            public void startNode(String name, Class clazz) {
                super.startNode(name, clazz);
            }

			@Override
            public String encodeNode(String name) {
                return name;
            }

            @Override
            protected void writeText(QuickWriter writer, String text) {
                if (cdata) {
                    writer.write("<![CDATA[");
                    writer.write(text);
                    writer.write("]]>");
                } else {
                    writer.write(text);
                }
            }
        };
	}
}
