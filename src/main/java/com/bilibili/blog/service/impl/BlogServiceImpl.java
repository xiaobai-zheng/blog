package com.bilibili.blog.service.impl;

import com.bilibili.blog.dao.BlogDao;
import com.bilibili.blog.pojo.Blog;
import com.bilibili.blog.service.BlogService;
import com.bilibili.blog.util.Msg;
import com.bilibili.blog.vo.BlogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogDao blogDao;
    @Override
    public Blog getBlog(Long id) {
        return null;
    }

    @Override
    public Page<Blog> listAllPage(Pageable pageable) {
        return blogDao.findAll(pageable);
    }

    @Override
    public Msg saveBlog(Blog blog) {
        return null;
    }

    @Override
    public Msg updateBlog(Blog blog) {
        return null;
    }

    @Override
    public void deleteBlog(Long id) {

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
                    predicates.add(cb.equal(root.<Long>get("typeId"), blogVo.getTypeId()));
                }
                if (blogVo.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get(""), blogVo.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
        return blogPage;
    }
}
