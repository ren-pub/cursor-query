package com.mlamp.cursor.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlamp.cursor.config.ThreadPoolConfig;
import com.mlamp.cursor.repository.bean.Order;
import com.mlamp.cursor.repository.bean.OrderDetail;
import com.mlamp.cursor.repository.mapper.OrderDetailMapper;
import com.mlamp.cursor.repository.mapper.OrderMapper;
import com.mlamp.cursor.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private ThreadPoolConfig threadPoolConfig;

    @Autowired
    private OrderDetailMapper orderDetailMapper;


    @Override
    public void batchSave() {
        for (int i = 0; i < 70; i++) {
            Order order = new Order("no_" + i, System.currentTimeMillis());
            baseMapper.insert(order);
            if (i % 3 == 0) {
                OrderDetail orderDetail = new OrderDetail(order.getId(), "北京市" + i + "区", "who" + i);
                orderDetailMapper.insert(orderDetail);
            }
            if (i % 3 == 1) {
                OrderDetail orderDetail = new OrderDetail(order.getId(), "北京市" + i + "区", "what" + i);
                orderDetailMapper.insert(orderDetail);

                OrderDetail orderDetail1 = new OrderDetail(order.getId(), "天津" + i + "区", "who1");
                orderDetailMapper.insert(orderDetail1);

            }
            if (i % 3 == 2) {
                OrderDetail orderDetail = new OrderDetail(order.getId(), "上海" + i + "区", "1what" + i);
                orderDetailMapper.insert(orderDetail);
                OrderDetail orderDetail1 = new OrderDetail(order.getId(), "广州" + i + "区", "1who1");
                orderDetailMapper.insert(orderDetail1);
                OrderDetail orderDetail2 = new OrderDetail(order.getId(), "深圳" + i + "区", "1who1");
                orderDetailMapper.insert(orderDetail2);

            }
        }
    }
}
