package com.lujinhong.commons.others.thrift;

import org.apache.thrift.TException;

import com.lujinhong.commons.others.thrift.HelloWorldService.Iface;

/**
* date: 2016年6月28日 下午2:33:26
* @author LUJINHONG lu_jin_hong@163.com
* Function: TODO
* last modified: 2016年6月28日 下午2:33:26
*/

public class HelloWorldImpl implements Iface {

	@Override
	public String sayHello(String username) throws TException {
		return "Hi "+username+", Welcome!";
	}

}
