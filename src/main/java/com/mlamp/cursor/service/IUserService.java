package com.mlamp.cursor.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlamp.cursor.repository.bean.User;

import java.sql.SQLException;

public interface IUserService extends IService<User> {
    void insertCustom() throws SQLException;


    void cursorQuery(Integer id) throws InterruptedException, SQLException;
    void cursorQueryAndWrite(Integer id) throws InterruptedException, SQLException;

    void cursorQueryAndPageWrite(Integer id);

    void cursorQueryAndBatchPageWrite(Integer id);


    void writeJ(Integer id);

    void cursorQueryR(Integer id);
}