package cn.esup.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Aop Test
 * 
 * @author yangq(qi.yang.cn@gmail.com)
 */
public class AopTest {

	/**
	 * Test Function
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		final ArrayList<String> aList = new ArrayList<String>();
		aList.add("China");
		aList.add("ZheJiang");

		/**
		 * 创建代理对象
		 */
		List list = (List) Proxy.newProxyInstance(	List.class.getClassLoader(),
													new Class[]{List.class},
													new InvocationHandler() {

														public Object invoke(	Object obj,
																				Method method,
																				Object[] args)
																throws Throwable {

															if ("size".equals(method.getName())) {// 拦截size方法

																System.out.println("Aop前置");

																/***
																 * 调用被代理对象及方法
																 */
																Object o = method.invoke(	aList,
																							args);

																System.out.println("Aop后置");
																return o;
															}

															return null;
														}
													});

		list.size();// 通过代理对象执行方法
	}
}
