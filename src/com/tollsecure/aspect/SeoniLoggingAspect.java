package com.tollsecure.aspect;

import java.io.IOException;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.tollsecure.entity.User;


@Aspect
@Component
public class SeoniLoggingAspect {

	//setup logger
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	//setup pointcut declarations
	@Pointcut("execution(* com.tollsecure.controller.RegisterAndLoginController.*(..))")
	private void forControllerPackage() {}

	//only for login page
	@Pointcut("execution(* com.tollsecure.controller.RegisterAndLoginController.loginPage(..))")
	private void forLoginPage() {}
	
	//now same for service and dao
	//@Pointcut("execution(* com.seoniproject.service.RegisterAndLoginController.*(..))")
	//private void forServicePackage() {}
	//@Pointcut("")
	//private void forDaoPackage() {}
	//@PointCut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	//private void forAppFlow() {}
	//exclude loginpage
	@Pointcut("forControllerPackage() && !forLoginPage()")
	private void exceptLoginPage() {}
	
	//add @Before advice
	@Before("exceptLoginPage()")
	public void before(JoinPoint theJoinPoint) {
		
		//display method we were calling
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("===>>> in @Before: calling method: "+theMethod);
		
		//display the arguments to the method
		Object[] args = theJoinPoint.getArgs();
		
		//loop thru the arguments
		for (Object tempArg: args) {
			myLogger.info("===>>> argument: "+tempArg);
		}
		
	}
	
	//add @AfterReturn advice
		@AfterReturning(
			pointcut="exceptLoginPage()",
			returning="theResult"
			)
		public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
			//display method we are returning from
			String theMethod = theJoinPoint.getSignature().toShortString();
			myLogger.info("===>>> in @AfterReturning: calling method: "+theMethod);
			//display data returned
			myLogger.info("===>> result: "+theResult);
		}
		
		//this is only for showHomePage method
		@SuppressWarnings("unused")
		@Before("execution(* com.tollsecure.controller.RegisterAndLoginController.showHomePage(..))")
		public void beforeShowHomePage(JoinPoint theJoinPoint) {
			Object[] args = theJoinPoint.getArgs();
			
			//looing through attributes
			for (Object tempArg: args) {
				if (tempArg instanceof User) {
					if (tempArg == null) {
						throw new ArithmeticException("not valid");  
					}
				}
			}
		}
}

















