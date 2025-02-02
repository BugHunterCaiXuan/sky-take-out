package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StatusMapper {

    @Update("update status set status = #{status} where id = #{id}")
    void update(Integer status, Long id);
}
