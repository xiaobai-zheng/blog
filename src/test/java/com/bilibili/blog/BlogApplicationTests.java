package com.bilibili.blog;

import com.bilibili.blog.util.MD5Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {

    @Test
    void contextLoads() {
        String code = MD5Utils.code("111111");
        System.out.println(code);
    }

}
