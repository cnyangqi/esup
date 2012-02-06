package cn.esup.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

/**
 * AOP服务类
 * 
 * 注意：类内方法调用不被拦截
 * @author yangq(qi.yang.cn@gmail.com)
 */
@Aspect
@Service
@Transactional
public class AopService {

	@Pointcut("execution(public * cn.esup.web.front.*Action.execute*(..))")
	public void execMethod() {

	}

	/** 保存来访用户记录 */
	@Around("execMethod()")
	public Object saveGuest(ProceedingJoinPoint pjp) throws Throwable {
		// System.out.println(pjp.getSignature().getName());
		Object retVal = pjp.proceed();
		System.out.println(SpringSecurityUtils.getCurrentUserName() + "," + SpringSecurityUtils.getCurrentUserIp());
		return retVal;
	}

	public static void main(String[] args) throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();
		scheduler.shutdown();
	}
}
