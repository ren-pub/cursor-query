package com.mlamp.cursor.repository.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlamp.cursor.dto.UserDto;
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
public interface UserMapper extends BaseMapper<User> {

    /**
     * FORWARD_ONLY：只允许游标向前访问
     * SCROLL_SENSITIVE：允许游标双向滚动，但不会及时更新数据，也就是说如果数据库中的数据被修改过，并不会在resultSet中体现出来
     * SCROLL_INSENSITIVE：许游标双向滚动，如果数据库中的数据被修改过，会及时更新到resultSet
     *
     * @param id
     * @return
     */
    @Options(resultSetType = ResultSetType.DEFAULT, fetchSize = Integer.MIN_VALUE)
    @Select({"select id, a, b, c, d, e, f, g, h, i, j, k from user where id > #{id}  order by id desc"})
    Cursor<User> getUser(Integer id);

    @Select({"select count(id) from user where id > #{id}  order by id desc"})
    Integer getCount(Integer id);


    Integer countJoinQuery(@Param("id") Integer id);

    Cursor<UserDto> selectDetailJoinQuery(@Param("id") Integer id);

}
