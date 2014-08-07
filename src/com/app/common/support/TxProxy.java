package com.app.common.support;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.plugin.IPlugin;

public class TxProxy implements IPlugin,MethodInterceptor,Interceptor{

	@Override
	public boolean start() {
		// TODO Auto-generated method stub
		System.out.println("tx start");
		return false;
	}

	@Override
	public boolean stop() {
		// TODO Auto-generated method stub
		System.out.println("tx stop");
		return false;
	}
	
	/**
	 * 根据class 创建对象的代理对象
	 * 1.设置父类, 2.设置回调
	 * 本质:动态创建了一个 clazz 对象的子类
	 * @param clazz
	 * @return
	 */
	public Object getProxy(Class clazz){
		
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		return enhancer.create();
	}
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("事务开始...");
		Object object = proxy.invokeSuper(obj, args);
		System.out.println("事务结束...");
		return object;
	}

	@Override
	public void intercept(ActionInvocation ai) {
		// TODO Auto-generated method stub
		TxProxy txProxy = new TxProxy();
		ActionInvocation action = (ActionInvocation) txProxy.getProxy(ai.getClass());
		action.invoke();
	}
	
}
