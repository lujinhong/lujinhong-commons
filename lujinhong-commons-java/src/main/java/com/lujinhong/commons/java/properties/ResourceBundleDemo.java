/**
 * 
 */
package com.lujinhong.commons.java.properties;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
* date: 2016年3月21日 下午6:16:12
* @author LUJINHONG lu_jin_hong@163.com
* Function: TODO ADD FUNCTION.
* last modified: 2016年3月21日 下午6:16:12
*/

public class ResourceBundleDemo {
	//注意：properties文件编码必须是ISO-8859-1

	public static void main(String[] args) {
		ResourceBundle rb = ResourceBundle.getBundle("person");
		System.out.println(rb.getString("name") + "\t" + rb.getString("age") + "\t" +rb.getString("gender") );
		
		Locale locale = new Locale("zh","CN");
		ResourceBundle rb2 = ResourceBundle.getBundle("person2",locale);
		System.out.println(rb2.getString("name") + "\t" + rb2.getString("age") + "\t" +rb2.getString("gender") );

		//当配置文件不在jar包中时
		BufferedInputStream inputStream;
		ResourceBundle rb3 = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream("/home/hadoop/conf/person.properties"));
			rb3  = new PropertyResourceBundle(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
