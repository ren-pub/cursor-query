package com.mlamp.cursor.repository.bean;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@TableName("order_main")
@Builder(toBuilder = true)
public class Order {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String orderNo;

    private Long creatTime;

    public Order(String orderNo, long creatTime) {
        this.orderNo = orderNo;
        this.creatTime = creatTime;
    }
}
