package org.jarvis.sqltask.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.jarvis.sqltask.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select(value = "select * from `data_status`")
    List<Object> selectUserAllList();

    @Insert(value = "insert into user (id,name) values (${id},'${name}')")
    int insertUser(User user);

}
