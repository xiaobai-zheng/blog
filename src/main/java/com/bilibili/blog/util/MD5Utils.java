package com.bilibili.blog.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密类
 * @param str 要加密的字符串
 * @return     加密后的字符串
 * */
public class MD5Utils {
    public static String code(String str){
        /*
8.     * 1.一个运用基本类的实例
9.     * MessageDigest 对象开始被初始化。该对象通过使用 update 方法处理数据。
10.     * 任何时候都可以调用 reset 方法重置摘要。
11.     * 一旦所有需要更新的数据都已经被更新了，应该调用 digest 方法之一完成哈希计算。
12.     * 对于给定数量的更新数据，digest 方法只能被调用一次。
13.     * 在调用 digest 之后，MessageDigest 对象被重新设置成其初始状态。
14.     */
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(str.getBytes());//update处理
                byte [] encryContext = md.digest();//调用该方法完成计算
                int i;
                StringBuffer buf = new StringBuffer("");
                for (int offset = 0; offset < encryContext.length; offset++) {//做相应的转化（十六进制）
                    i = encryContext[offset];
                    if (i < 0) i += 256;
                    if (i < 16) buf.append("0");
                    buf.append(Integer.toHexString(i));
                }
                return buf.toString();
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
    }


}
