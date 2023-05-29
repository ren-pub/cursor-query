package com.mlamp.cursor.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.MybatisAbstractSQL;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlamp.cursor.dto.OrderDetailDto;
import com.mlamp.cursor.excel.ExcelFillCellMergeStrategy;
import com.mlamp.cursor.repository.bean.Order;
import com.mlamp.cursor.repository.bean.OrderDetail;
import com.mlamp.cursor.repository.bean.User;
import com.mlamp.cursor.repository.mapper.OrderDetailMapper;
import com.mlamp.cursor.repository.mapper.OrderMapper;
import com.mlamp.cursor.repository.mapper.UserMapper;
import com.mlamp.cursor.service.IOrderService;
import jdk.nashorn.tools.Shell;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.transaction.annotation.Propagation.NESTED;

@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private OrderDetailMapper orderDetailMapper;


    @Autowired
    private UserMapper userMapper;


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


    @Override
    public void mergeDataExport(Integer mId) {

        Integer total = orderDetailMapper.countJoinQuery(mId);
        log.info("总条数:{}", total);
        StopWatch watch = new StopWatch();
        watch.start("cursor-query");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //2、在连接中开启游标查询
        Cursor<OrderDetailDto> cursor = sqlSession.getMapper(OrderDetailMapper.class).selectJoin(mId);

        File file = new File("xlsx/a_" + System.currentTimeMillis() + ".xlsx");
        ExcelWriter writer = EasyExcel.write(file)
                .excelType(ExcelTypeEnum.XLSX).registerWriteHandler(new ExcelFillCellMergeStrategy(1, new int[]{0, 1, 2})).needHead(false).build();
        WriteSheet sheet = EasyExcel.writerSheet("订单数据").build();

        //设置 每10个订单写出一次
        int writeControl = 0;
        // 第一条数据对比时 -> 执行isSameOrder  结果 fasle
        Integer c = -1;

        List<OrderDetailDto> writeDto = new ArrayList<>();
        for (OrderDetailDto dto : cursor) {
            // 当订单变化时对数据赋值
            if (!dto.isSameOrder(c)) {
                writeControl++;

                // 满足10条订单写出一次
                if (writeControl == 10) {
                    log.info("10个订单执行一次数据导出");
                    writer.write(writeDto, sheet);
                    // 写出后对之前的数据做清楚处理
                    writeDto.clear();
                    writeControl = 0;
                }
            }
            if (writeControl == 0) {
                c = -1;
            } else {
                c = dto.getMainId();
            }

            writeDto.add(dto);

            // 判断是不是最后一次写出数据 并且writeControl 不能是0
            if ((cursor.getCurrentIndex() == total - 1) && writeControl != 0) {
                writer.write(writeDto, sheet);
                log.info("当读取到最后一条数据后 最后一次写出,最后一次处理剩余多少写出多少");
                writeDto.clear();
            }
        }
        writer.finish();

        watch.stop();

        log.info("游标查询  合并单元格数据 批量导出  导出数据时间:{}", watch);
        try {
            if (cursor.isOpen()) {
                log.info("关闭流");
                cursor.close();
            }
        } catch (IOException e) {
            log.error("close error", e);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
   // @Transactional(rollbackFor = Exception.class, propagation = NESTED)
    public void updateUser() {
     
        User user = new User();
        user.setId(10);
        user.setA("我是order里面修改后的数据呀");
        userMapper.updateById(user);

        System.out.println("我是order修改后的a    " + user.getA());
        //throw new RuntimeException("手动异常");

    }
}
