package com.mlamp.cursor;

import com.mlamp.cursor.repository.bean.User;
import com.mlamp.cursor.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CursorApplicationTests {

    @Autowired
    IUserService iUserService;

    @Test
    public void contextLoads() {
        User user = iUserService.selectById(1);

    }


    @Test
    public void test1() {
        System.out.println(1122);

    }


    @Test
    public void test2() {
        System.out.println(1122);

    }


    @Test
    public void test3() {
        System.out.println(1122);

    }

    @Test
    public void test4() {
        throw new RuntimeException("Exception");

    }


}
