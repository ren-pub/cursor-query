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
@TableName("order_detail")
@Builder(toBuilder = true)
public class OrderDetail {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer orderId;

    private String address;

    private String user;

    public OrderDetail(Integer orderId, String address, String user) {
        this.orderId = orderId;
        this.address = address;
        this.user = user;
    }
}
