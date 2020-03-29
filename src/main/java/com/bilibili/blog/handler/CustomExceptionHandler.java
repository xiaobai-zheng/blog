package com.bilibili.blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e){
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.error("mRequestUrl:{} \n mException:{}",request.getRequestURI(),e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/error");
        modelAndView.addObject("mException",e);
        modelAndView.addObject("mUrl",request.getRequestURI());
        return modelAndView;
    }
}
