/**
 * 
 */
package com.lujinhong.commons.java.autoclosable;

/**
* date: 2016年2月29日 下午4:28:49
* @author LUJINHONG lu_jin_hong@163.com
* Function: TODO ADD FUNCTION.
* last modified: 2016年2月29日 下午4:28:49
*/

public class MyResource implements AutoCloseable{
	//在try()中创建MyResource对象，当try模块语句结束时，close()方法会被自动调用。
	 @Override  
	 public void close() throws Exception {  
	  System.out.println("资源被关闭了！");  
	 }  
	   
	 public void doSomething(){  
	  System.out.println("干活了！");  
	 }  
}
