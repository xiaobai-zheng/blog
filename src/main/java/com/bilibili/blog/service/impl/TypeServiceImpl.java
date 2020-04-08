package com.bilibili.blog.service.impl;

import com.bilibili.blog.dao.TypeDao;
import com.bilibili.blog.exception.CustomException;
import com.bilibili.blog.exception.CustomExceptionCodeImp;
import com.bilibili.blog.pojo.Type;
import com.bilibili.blog.service.TypeService;
import com.bilibili.blog.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeDao typeDao;
    @Override
    @Transactional
    public Type getType(Long id) {
        return typeDao.getOne(id);
    }

    @Override
    @Transactional
    public Page<Type> listPage(Pageable pageable) {
        return typeDao.findAll(pageable);
    }
    @Override
    @Transactional
    public Msg saveType(Type type) {
        Type save = typeDao.save(type);
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
    public Msg updateType(Type type) {
        Type one = typeDao.getOne(type.getId());
        if (one == null){
            throw new CustomException(CustomExceptionCodeImp.FIND_NOT_TYPE_EXCEPTION);
        }
        Type save = typeDao.save(type);
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
    public void deleteType(Long id) {
        typeDao.deleteById(id);
    }

    @Override
    public Type getTypeByName(String name) {
       Type type = typeDao.findByName(name);
        return type;
    }

    @Override
    public List<Type> listAll() {
        return typeDao.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(0,size,sort);
        return typeDao.findByBlogsSize(pageable);
    }
}
