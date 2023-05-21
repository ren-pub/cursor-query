package com.mlamp.cursor.controller;

import com.mlamp.cursor.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("order")
public class OrderController {

    @Autowired
    private IOrderService iOrderService;


    /**
     * 批量构造测试数据
     *
     * @return
     */
    @GetMapping("add")
    public String add() {
        iOrderService.batchSave();
        return "success";
    }


}

