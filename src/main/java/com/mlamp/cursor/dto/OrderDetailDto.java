package com.mlamp.cursor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrderDetailDto {


    private Integer mainId;

    private String orderNo;

    private Long creatTime;


    private Integer id;


    private String address;

    private String user;


    public boolean isSameOrder(Integer orderId) {
        /**
         * 第一次对比数据
         */
        if (-1 == orderId) {
            return false;
        }
        return Objects.equals(this.mainId, orderId);
    }
}
