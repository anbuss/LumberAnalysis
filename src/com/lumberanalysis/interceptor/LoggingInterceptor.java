/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumberanalysis.interceptor;

import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

/**
 *
 * @author samidoss
 */
public class LoggingInterceptor implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {

    private static Logger log = null;

    public LoggingInterceptor() {
    }

    public void before(Method arg0, Object[] arg1, Object arg2) throws Throwable {
        log = Logger.getLogger(arg2.getClass());
        log.info("Beginning method: " + arg0.getName());
    }

    public void afterReturning(Object arg0, Method arg1, Object[] arg2, Object arg3) throws Throwable {
        log = Logger.getLogger(arg3.getClass());
        log.info("Ending method: " + arg1.getName());
    }

    public void afterThrowing(Method m, Object[] args, Object target, Throwable ex) {
        log = Logger.getLogger(target.getClass());
        log.info("Exception in method: " + m.getName() + " Exception is: " + ex.getMessage());
    }
}
