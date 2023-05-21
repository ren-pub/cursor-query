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
@TableName("user")
@Builder(toBuilder = true)
public class User {

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
    private int sex;
    /**
     * 职业
     */
    private String career;


    public User(String a, String b, String c
            , String d, String e, String f
            , String g, String h, String i
            , String j, String k, Integer age
            , Integer sex, String career) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
        this.h = h;
        this.i = i;
        this.j = j;
        this.k = k;
        this.age = age;
        this.sex = sex;
        this.career = career;
    }
}
