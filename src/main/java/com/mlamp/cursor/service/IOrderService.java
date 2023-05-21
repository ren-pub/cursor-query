package com.mlamp.cursor.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlamp.cursor.repository.bean.Order;
import com.mlamp.cursor.repository.bean.User;

import java.sql.SQLException;

public interface IOrderService extends IService<Order> {

    void batchSave();
}