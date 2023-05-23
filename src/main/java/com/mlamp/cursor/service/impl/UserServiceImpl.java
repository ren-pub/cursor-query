package com.mlamp.cursor.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlamp.cursor.config.ThreadPoolConfig;
import com.mlamp.cursor.dto.UserDto;
import com.mlamp.cursor.enums.CareerEnum;
import com.mlamp.cursor.repository.bean.User;
import com.mlamp.cursor.repository.mapper.UserMapper;
import com.mlamp.cursor.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private ThreadPoolConfig threadPoolConfig;

    @Override
    public void insertCustom() throws SQLException {

        List<User> users = buildUsers();

        int j = 0;
        while (j < 50) {
            int finalJ = j;
            threadPoolConfig.writeDb().execute(() -> {
                StopWatch watch = new StopWatch();
                watch.start("batch_insert" + finalJ);
                String sql = "insert into user (a, b, c, d, e, f, g, h, i, j, k,age,sex,career) value (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
                jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                    @Override
                    public int getBatchSize() {
                        return users.size();
                    }

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, users.get(i).getA());
                        ps.setString(2, users.get(i).getB());
                        ps.setString(3, users.get(i).getC());
                        ps.setString(4, users.get(i).getD());
                        ps.setString(5, users.get(i).getE());
                        ps.setString(6, users.get(i).getF());
                        ps.setString(7, users.get(i).getG());
                        ps.setString(8, users.get(i).getH());
                        ps.setString(9, users.get(i).getI());
                        ps.setString(10, users.get(i).getJ());
                        ps.setString(11, users.get(i).getK());
                        ps.setInt(12, users.get(i).getAge());
                        ps.setInt(13, users.get(i).getSex());
                        ps.setString(14, users.get(i).getCareer());
                    }
                });
                watch.stop();
                log.info("批量插入时间详情:{}", watch);
            });
            j++;
        }

    }

    @Override
    public void cursorQuery(Integer id) throws InterruptedException, SQLException {

        //写第一个sheet
        Integer total = baseMapper.getCount(id);
        log.info("总条数:{}", total);
        StopWatch watch = new StopWatch();
        watch.start("cursor-query");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //2、在连接中开启游标查询
        Cursor<User> cursor = sqlSession.getMapper(UserMapper.class).getUser(id);

        // 模拟打印 主要是为了看JVM堆内存趋势
        cursor.forEach(System.out::println);

        watch.stop();
        log.info("游标处理导出数据时间:{}", watch);
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
    public void cursorQueryAndWrite(Integer id) throws InterruptedException, SQLException {
        // 创建一个表格
        //写第一个sheet
        Integer total = baseMapper.getCount(id);
        log.info("总条数:{}", total);
        StopWatch watch = new StopWatch();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //2、在连接中开启游标查询
        Cursor<User> cursor = sqlSession.getMapper(UserMapper.class).getUser(id);

        List<User> users_temp = new ArrayList<>();
        File file = new File("xlsx/a_" + System.currentTimeMillis() + ".xlsx");
        ExcelWriter writer = EasyExcel.write(file).excelType(ExcelTypeEnum.XLSX).needHead(false).build();
        // 主子表导出性能优化 TODO
        WriteSheet sheet = EasyExcel.writerSheet("用户数据").build();
        watch.start("cursor-query");
        for (User user : cursor) {

            users_temp.add(user);
            if (users_temp.size() == 500) {
                log.info("每满足{}条写到磁盘一次", 500);
                writer.write(users_temp, sheet);
                users_temp.clear();
            } else if (cursor.getCurrentIndex() == total - 1) {
                writer.write(users_temp, sheet);
                log.info("当读取到最后一条数据后 最后一次写出,最后一次处理剩余多少写出多少");
                users_temp.clear();
            }

        }
        watch.stop();
        writer.finish();

        log.info("游标处理导出数据时间:{}", watch);
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
    public void cursorQueryAndPageWrite(Integer id) {
        EntityWrapper<User> ew = new EntityWrapper<>();
        if (id != null) {
            ew.ge("id", id);
        }
        ew.orderDesc(Collections.singletonList("id"));
        StopWatch watch = new StopWatch();
        ArrayList<User> allUser = new ArrayList<>();
        int offset = 0;
        int limit = 1000;

        while (true) {
            log.info("当前查询到第多少次:{}", offset);
            watch.start("page-query" + offset);
            List<User> list = baseMapper.selectPage(new RowBounds(offset, limit), ew);
            if (list != null && list.size() > 0) {
                allUser.addAll(list);
            }
            if (list != null && list.size() < limit) {
                break;
            }
            offset++;
            watch.stop();
        }
        log.info("导出数据Mock..............");
        File file = new File("xlsx/a_" + System.currentTimeMillis() + ".xlsx");

        /***
         * 在程序数据量较大的时候  allUser对象会导致无数次GC 线程压根执行不到此处！！！！
         */
        ExcelWriter writer = EasyExcel.write(file).excelType(ExcelTypeEnum.XLSX).needHead(false).build();
        WriteSheet sheet = EasyExcel.writerSheet("用户数据").build();
        writer.write(allUser, sheet);
        writer.finish();
    }

    @Override
    public void cursorQueryAndBatchPageWrite(Integer id) {
        EntityWrapper<User> ew = new EntityWrapper<>();
        if (id != null) {
            ew.ge("id", id);
        }
        ew.orderDesc(Collections.singletonList("id"));
        StopWatch watch = new StopWatch();
        watch.start("page-write");
        int current = 0;
        int limit = 5000;


        File file = new File("xlsx/a_" + System.currentTimeMillis() + ".xlsx");
        ExcelWriter writer = EasyExcel.write(file).excelType(ExcelTypeEnum.XLSX).needHead(false).build();
        WriteSheet sheet = EasyExcel.write(file).sheet("用户数据").build();

        while (true) {
            List<User> list = baseMapper.selectPage(new Page<>(current, limit), ew);
            if (list != null && list.size() > 0) {
                // 这种情况有个弊端   单元格不好合并  需要用一堆变量控制单元格合并
                log.info("导出数据...................");
                writer.write(list, sheet);
            }
            if (list != null && list.size() < limit) {
                break;
            }
            current++;
        }
        writer.finish();

        watch.stop();
        log.info("分页导出数据时间:{}", watch);
    }

    @Override
    public void writeJ(Integer id) {
        if (id == null) {
            // 设置默认值
            id = 1;
        }
        // 创建一个表格
        //写第一个sheet
        Integer total = baseMapper.countJoinQuery(id);
        log.info("总条数:{}", total);
        StopWatch watch = new StopWatch();
        watch.start();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //2、在连接中开启游标查询
        Cursor<UserDto> cursor = sqlSession.getMapper(UserMapper.class).selectDetailJoinQuery(id);

        List<UserDto> users_temp = new ArrayList<>();
        File file = new File("xlsx/a_" + System.currentTimeMillis() + ".xlsx");
        ExcelWriter writer = EasyExcel.write(file).excelType(ExcelTypeEnum.XLSX).needHead(false).build();
        WriteSheet sheet = EasyExcel.write(file).sheet("用户数据").build();
        for (UserDto user : cursor) {
            users_temp.add(user);
            if (users_temp.size() == 500) {
                log.info("每满足{}条写到磁盘一次", 500);
                writer.write(users_temp, sheet);
                users_temp.clear();
            } else if (cursor.getCurrentIndex() == total - 1) {
                writer.write(users_temp, sheet);
                log.info("当读取到最后一条数据后 最后一次写出,最后一次处理剩余多少写出多少");
                users_temp.clear();
            }
        }
        watch.stop();
        writer.finish();

        log.info("游标处理导出数据时间:{}", watch);
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
    public void cursorQueryR(Integer id) {

        //写第一个sheet
        Integer total = baseMapper.getCount(id);
        log.info("总条数:{}", total);
        StopWatch watch = new StopWatch();
        watch.start("cursor-query");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //2、在连接中开启游标查询
        Cursor<User> cursor = sqlSession.getMapper(UserMapper.class).getUser(id);

        List<User> users_temp = new ArrayList<>();

        for (User user : cursor) {
            // log.info("游标查询行:{},数据详情:{}", cursor.getCurrentIndex(), user);
            users_temp.add(user);
            if (users_temp.size() == 10) {
                log.info("每满足十条处理一次");
                users_temp.clear();
            } else if (cursor.getCurrentIndex() == total - 1) {
                // 最后一次 不满足十条
                log.info("当读取到最后一条数据后 最后一次处理");
            }
        }

        watch.stop();
        log.info("游标处理导出数据时间:{}", watch);
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
    @Transactional(readOnly = true)
    public void stream(Integer id) {

        baseMapper.selectStream(id, resultContext -> {
            int resultCount = resultContext.getResultCount();
            log.info("流式查询获取数据的量级:{}", resultCount);
            try {
                //逻辑代码
                UserDto resultObject = resultContext.getResultObject();
            } catch (Exception e) {
                log.error("XX发生错误,原因: {}", e.getMessage());
                e.printStackTrace();
            }

        });
    }


    private static List<User> buildUsers() {
        CareerEnum[] values = CareerEnum.values();

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            long l = System.currentTimeMillis();
            int age = RandomUtil.randomInt(20, 100);
            int sex = i % 2;

            CareerEnum career = values[i % 7];
            users.add(new User("Common a" + l
                    , "Common b" + l
                    , "Common c" + l, " Common d" + l
                    , "Common e" + l
                    , "Common f" + l
                    , "Common g" + l
                    , "Common h" + l
                    , "Common i", "Common j" + l
                    , "Common k" + l
                    , age, sex, career.name()));
        }
        return users;
    }

}
