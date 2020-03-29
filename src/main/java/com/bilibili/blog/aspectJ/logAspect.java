package com.bilibili.blog.aspectJ;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class logAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
//    切点
    @Pointcut("execution(* com.bilibili.blog.controller.*.*(..))")
    public void logPointcut(){}
//    前置增强
    @Before("logPointcut()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURI();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        ReturnLog returnLog = new ReturnLog(url,ip,classMethod,args);
        logger.info("request;{}",returnLog);
    }
//    后置增强
    @After("logPointcut()")
    public void doAfter(){
//        logger.info("---------------后置-----------------");
    }

    @AfterReturning(returning = "r",pointcut = "logPointcut()")
    public void doAfterReturning(Object r){
        logger.info("return:{}",r);
    }
    private class ReturnLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public ReturnLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "ReturnLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
