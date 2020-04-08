package com.bilibili.blog.service.impl;

import com.bilibili.blog.dao.TagDao;
import com.bilibili.blog.exception.CustomException;
import com.bilibili.blog.exception.CustomExceptionCodeImp;
import com.bilibili.blog.pojo.Tag;
import com.bilibili.blog.service.TagService;
import com.bilibili.blog.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.ListUtils;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<Tag> listAll() {
        return tagDao.findAll();
    }

    @Override
    @Transactional
    public List<Tag> listByIds(String tagIds) {
        //获取数据库中的标签
        List<Tag> tags = tagDao.findAll();

//        获取数据库中的所有id
        List<String> idAll = tags.stream().map(tag -> tag.getId().toString()).collect(Collectors.toList());

//        将id字符串转变成字符串数组
        String[] ids = StringUtils.split(tagIds, ",");
        List<String> idStrList = new ArrayList<>();
        for (String id : ids) {
            idStrList.add(id);
        }

//将不是数据库中的id筛选出来，变成标签名字符串数据
       List<String> addNames = idStrList.stream().filter(id -> !idAll.contains(id)).collect(Collectors.toList());
        //        将id字符串数组转变成list集合
        idStrList.removeAll(addNames);
        List<Long> idList = idStrList.stream().map(id -> Long.valueOf(id)).collect(Collectors.toList());
        List<Tag> tagList = tagDao.findAllById(idList);

        if (!ListUtils.isEmpty(addNames)){
            List<Tag> tagList1 = new ArrayList<>();
            Tag tag = new Tag();
            for (String name : addNames) {
                tag.setName(name);
                tagList1.add(tag);
                tagDao.saveAll(tagList1);
            }
        }
        return tagList;
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(0,size,sort);
        return tagDao.findByBlogsSize(pageable);
    }
}
