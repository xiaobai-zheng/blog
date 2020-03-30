package com.bilibili.blog.vo;

import lombok.Data;

@Data
public class BlogVo {
    private String title;
    private Long typeId;
    private boolean recommend;
}
