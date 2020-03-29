package com.bilibili.blog.service;

import com.bilibili.blog.pojo.Tag;
import com.bilibili.blog.util.Msg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {
    Tag getTag(Long id);
    Page<Tag> listPage(Pageable pageable);
    Msg saveTag(Tag Tag);
    Msg updateTag(Tag Tag);
    void deleteTag(Long id);

    Tag getTagByName(String name);
}
