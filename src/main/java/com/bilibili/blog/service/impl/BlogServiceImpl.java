package com.bilibili.blog.service.impl;

import com.bilibili.blog.dao.BlogDao;
import com.bilibili.blog.exception.CustomException;
import com.bilibili.blog.exception.CustomExceptionCodeImp;
import com.bilibili.blog.pojo.Blog;
import com.bilibili.blog.pojo.Tag;
import com.bilibili.blog.pojo.Type;
import com.bilibili.blog.pojo.User;
import com.bilibili.blog.service.BlogService;
import com.bilibili.blog.util.MarkdownUtils;
import com.bilibili.blog.util.Msg;
import com.bilibili.blog.vo.BlogSubmit;
import com.bilibili.blog.vo.BlogVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.*;
import java.sql.Time;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogDao blogDao;

    @Override
    public List<Blog> listByBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return blogDao.findByUpdateTime(pageable);
    }

//    根据id获取blog,将博客内容格式有markdown格式改为HTML格式
    @Override
    @Transactional
    public Blog getAndConvert(Long id) {
        Blog one = blogDao.getOne(id);
        if (one == null){
            throw new CustomException(CustomExceptionCodeImp.FIND_NOT_BLOG_EXCEPTION);
        }
        Blog blog = new Blog();
        BeanUtils.copyProperties(one,blog);
        String content = blog.getContent();
        content = MarkdownUtils.markdownToHtmlExtensions(content);
        blog.setViews(blog.getViews()+1);
        blog.setContent(content);
        blogDao.updateViews(id);
        return blog;
    }

    @Override
    public Blog getBlog(Long id) {
        return blogDao.getOne(id);
    }

    @Override
    public Page<Blog> listAllPage(Pageable pageable) {
        return blogDao.findAll(pageable);
    }

    @Transactional
    @Override
    public Msg saveBlog(BlogSubmit blogSubmit, User user, Type type, List<Tag> tags) {
        Msg msg = null;
        if(blogSubmit.getId()==null){
            Time time = new Time(System.currentTimeMillis());
            Blog blog = new Blog();
            BeanUtils.copyProperties(blogSubmit,blog);
            blog.setUser(user);
            blog.setType(type);
            blog.setTags(tags);
            blog.setUpdateTime(time);
            blog.setCreateTime(time);
            Blog save = blogDao.save(blog);
            if (save != null){
                msg = Msg.success();
                msg.setMessage("成功添加了一个新博客");
            }else{
                msg = Msg.fail();
                msg.setMessage("添加博客失败");
            }
        }else{
            Blog blog = blogDao.getOne(blogSubmit.getId());
            blog.setUpdateTime(new Time(System.currentTimeMillis()));
            BeanUtils.copyProperties(blogSubmit,blog);
            Blog save = blogDao.save(blog);
            if (save != null){
                msg = Msg.success();
                msg.setMessage("修改博客成功");
            }else{
                msg = Msg.fail();
                msg.setMessage("修改博客失败");
            }
        }

        return msg;
    }
    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogDao.deleteById(id);
    }

    @Override
    public Page<Blog> listSearch(Pageable pageable, BlogVo blogVo) {
        Page<Blog> blogPage = blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(blogVo.getTitle())) {
                    predicates.add(cb.like(root.<String>get("title"), "%" + blogVo.getTitle() + "%"));
                }
                if (blogVo.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blogVo.getTypeId()));
                }
                if (blogVo.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blogVo.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
        return blogPage;
    }

    @Override
    public Page<Blog> listSearch(Pageable pageable, String query) {
        return blogDao.findByQuery("%"+query+"%",pageable);
    }

    @Override
    public Page<Blog> listSearch(Pageable pageable, Long id) {
        Page<Blog> blogPage = blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"), id);
            }
        }, pageable);
        return blogPage;
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
       List<String> years = blogDao.findGroupYear();
        Map<String, List<Blog>> map = new LinkedHashMap<>();
        for (String year : years) {
            map.put(year,blogDao.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogDao.count();
    }
}
