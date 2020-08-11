package com.meric.deepinjava.springboot.mybatis.mapper;

import org.apache.ibatis.annotations.Select;

public interface TestMapper {
    @Select("select * from xxx")
    public Object query();
}
