package com.bilibili.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by time on 2020/3/24
 * */
@Entity
@Table(name = "tb_blog")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;//内容
    private String firstPicture;//首图地址
    private String flag;//标记；原创，转载，翻译
    private Integer views;//浏览次数
    private boolean appreciation;//是否赞赏
    private boolean shareStatement;//转载声明是否开启
    private boolean commentabled;//是否可以评论
    private boolean published;//是否发布
    private boolean recommend;//是否推荐
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;//修改时间
    @ManyToOne
    private Type type;
    //级联新增，当添加了一个博客的时候，添加了一个tag，将新增tag自动表中
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments;

}
