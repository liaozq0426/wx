package com.gavin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @title Map对象 和 xml字符串之间相互转换
 * @author gavin
 * @date 2019年4月21日
 */
public class BeanXmlUtil {
	
	
	/**
	 * @title  从HttpServletRequest中获取xml数据流，并转换为String对象
	 * @author gavin
	 * @date 2019年4月22日
	 * @param request
	 * @return xml字符串
	 */
	public static String xmltoString(HttpServletRequest request) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while((line = br.readLine()) != null) {
				sb.append(line+"\n");
			}
			br.close();
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * @title 从HttpServletRequest中获取xml数据流，并转换为map对象
	 * @author gavin
	 * @date 2019年4月21日
	 * @param request
	 * @return map对象
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();
        try {
            InputStream ins = request.getInputStream();

            Document doc = reader.read(ins);
            Element root = doc.getRootElement();

            @SuppressWarnings("unchecked")
            List<Element> list = root.elements();
            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
	
	/**
	 * @title 将xml字符串转换为map对象
	 * @author gavin
	 * @date 2019年4月21日
	 * @param request
	 * @return map对象
	 */
	public static Map<String, String> xmlToMap(String xml) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            // 将字符串转为xml
            Document doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();

            @SuppressWarnings("unchecked")
            List<Element> list = root.elements();
            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
	
	
    /**
     * @title 将object对象转换为xml字符串
     * @desc 不进行 双下划线、CDATA标记处理
     * @author gavin
     * @date 2019年4月21日
     * @param object
     * @return xml字符串
     */
    public static String beanToXmlDefault(Object object) {
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("xml", object.getClass());
        return xStream.toXML(object);
    }
    
    /**
     * @title 将object对象转换为xml字符串
     * @desc  对 双下划线 问题进行处理，并进行CDATA标记处理，根节点自动设置名字为xml
     * @author gavin
     * @date 2019年4月21日
     * @param object
     * @return xml字符串
     */
    public static String beanToXmlCommon(Object object) {
    	XStream xStream = new CommonXStream(new CommonXppDriver(new NoNameCoder()) ,object.getClass());
    	return xStream.toXML(object);
    }
}
