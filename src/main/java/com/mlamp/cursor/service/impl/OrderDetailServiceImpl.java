package com.mlamp.cursor.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlamp.cursor.config.ThreadPoolConfig;
import com.mlamp.cursor.repository.bean.OrderDetail;
import com.mlamp.cursor.repository.mapper.OrderDetailMapper;
import com.mlamp.cursor.service.IOrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {


}
