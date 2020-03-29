package com.bilibili.blog.util;

import java.util.HashMap;
import java.util.Map;

public class Msg {
    //状态码，100表示成功 200表示失败
    private int code;
    private String message;
    private Map<String,Object> extend = new HashMap<>();

    public static Msg success(){
        Msg result = new Msg();
        result.setCode(100);
        result.setMessage("恭喜您，您的操作成功了");
        return result;
    }
    public static Msg fail(){
        Msg result = new Msg();
        result.setCode(200);
        result.setMessage("操作失败");
        return result;
    }
    public Msg add(String key, Object object){
        this.getExtend().put(key,object);
        return this;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }
}
