package com.mlamp.cursor.repository.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlamp.cursor.dto.UserDto;
import com.mlamp.cursor.repository.bean.Order;
import com.mlamp.cursor.repository.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.ResultSetType;

/**
 * @author 0004171
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
