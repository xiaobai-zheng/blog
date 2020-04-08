package com.bilibili.blog.exception;

public enum CustomExceptionCodeImp implements CustomExceptionCode {
    FIND_NOT_TYPE_EXCEPTION(601,"对不起，你要修改的类型不存在！！！！"),
    FIND_NOT_Tag_EXCEPTION(602,"对不起，你要修改的标签不存在！！！！"),
    FIND_NOT_BLOG_EXCEPTION(603,"对不起，你要查找的博客不存在" );
    private String message;
    private Integer code;
    CustomExceptionCodeImp(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

}
