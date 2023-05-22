package com.mlamp.cursor.controller;

import com.mlamp.cursor.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController

@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @GetMapping
    public String getUser() {
        iUserService.selectById(1);
        return "success";
    }


    /**
     * 批量构造测试数据
     *
     * @return
     */
    @GetMapping("add")
    public String add() {
        try {
            iUserService.insertCustom();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "success";
    }


    /**
     * 基于游标查询的实现
     *
     * @return
     */
    @GetMapping("query")
    public String cursorQuery(@RequestParam(required = false, defaultValue = "0") Integer id) {
        try {

            iUserService.cursorQuery(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "success";
    }


    /**
     * 基于游标查询的实现以及 模拟采用List接收导出
     *
     * @param id
     * @return
     */
    @GetMapping("queryR")
    public String cursorQueryR(@RequestParam(required = false, defaultValue = "0") Integer id) {
        try {

            iUserService.cursorQueryR(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "success";
    }


    /**
     * 游标查询  并实现基于EasyExcel的数据批量导出
     *
     * @param id
     * @return
     */
    @GetMapping("write")
    public String write(@RequestParam(required = false, defaultValue = "0") Integer id) {
        try {
            iUserService.cursorQueryAndWrite(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "end";
    }


    /**
     * 多次分页 一次导出
     *
     * @param id
     * @return
     */
    @GetMapping("page/w")
    public String w(@RequestParam(required = false, defaultValue = "0") Integer id) {
        try {
            iUserService.cursorQueryAndPageWrite(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "end";
    }


    /***
     * 多次分页查询  多次导出数据
     * @param id
     * @return
     */
    @GetMapping("page/batchW")
    public String batchW(@RequestParam(required = false, defaultValue = "0") Integer id) {
        try {
            iUserService.cursorQueryAndBatchPageWrite(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "end";
    }


    /**
     * 上面接口 ↑ 的查询都是基于单表实现的  此处写了个表关联查询 用于模拟googi-mes环境的多表查询 看一下多表游标查询的效果
     *
     * @param id
     * @return
     */
    //join.-------  关联查询导出  使用游标方式
    @GetMapping("writeJ")
    public String writeJ(@RequestParam(required = false, defaultValue = "0") Integer id) {

        try {
            iUserService.writeJ(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "end";
    }


    /**
     * 流式查询
     *
     * @param id
     * @return
     */
    @GetMapping("stream")
    public String stream(@RequestParam(required = false, defaultValue = "0") Integer id) {
        iUserService.stream(id);
        return "success";
    }


}

