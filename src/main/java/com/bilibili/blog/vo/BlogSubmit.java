package com.bilibili.blog.vo;

import lombok.Data;

@Data
public class BlogSubmit {
    private Long id;
    private String title;
    private String content;//内容
    private String firstPicture;//首图地址
    private String flag;//标记；原创，转载，翻译
    private Long typeId;//分类id
    private String tagIds;//标签id的字符串
    private boolean appreciation;//是否赞赏
    private boolean shareStatement;//转载声明是否开启
    private boolean commentabled;//是否可以评论
    private boolean published;//是否发布
    private boolean recommend;//是否推荐
    private String description;//博客描述
}
