package com.neu.mapper;

import com.neu.dto.TestingDto;
import com.neu.pojo.Testing;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TestingMapper {
    void record(@Param("testing") Testing testing);

    List<Testing> getAllTest();

    TestingDto getAllByExId(@Param("messageId") Integer messageId);

}
