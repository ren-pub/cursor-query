package com.mlamp.cursor.dto;

import com.baomidou.mybatisplus.annotations.TableId;
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
@Builder(toBuilder = true)
public class UserDto {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;


    /**
     * 年纪
     */
    private int age;
    /**
     * 性别
     */
    private String sexName;
    /**
     * 职业
     */
    private String careerCh;

    private String careerEN;


}
