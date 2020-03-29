package com.bilibili.blog.service.impl;

import com.bilibili.blog.dao.TagDao;
import com.bilibili.blog.exception.CustomException;
import com.bilibili.blog.exception.CustomExceptionCodeImp;
import com.bilibili.blog.pojo.Tag;
import com.bilibili.blog.service.TagService;
import com.bilibili.blog.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagDao tagDao;
    @Override
    @Transactional
    public Tag getTag(Long id) {
        return tagDao.getOne(id);
    }

    @Override
    @Transactional
    public Page<Tag> listPage(Pageable pageable) {
        return tagDao.findAll(pageable);
    }
    @Override
    @Transactional
    public Msg saveTag(Tag tag) {
        Tag save = tagDao.save(tag);
        Msg msg = null;
        if (save == null){
            msg = Msg.fail();
            msg.setMessage("对不起，添加失败");
        }else{
            msg = Msg.success();
            msg.setMessage("恭喜您，添加成功");
        }
        return msg;
    }

    @Override
    @Transactional
    public Msg updateTag(Tag tag) {
        Tag one = tagDao.getOne(tag.getId());
        if (one == null){
            throw new CustomException(CustomExceptionCodeImp.FIND_NOT_Tag_EXCEPTION);
        }
        Tag save = tagDao.save(tag);
        Msg msg = null;
        if (save == null){
            msg = Msg.fail();
            msg.setMessage("对不起,修改失败");
        }else{
            msg = Msg.success();
            msg.setMessage("恭喜您，修改成功");
        }
        return msg;
    }

    @Override
    @Transactional
    public void deleteTag(Long id) {
        tagDao.deleteById(id);
    }

    @Override
    public Tag getTagByName(String name) {
       Tag tag = tagDao.findByName(name);
        return tag;
    }
}
