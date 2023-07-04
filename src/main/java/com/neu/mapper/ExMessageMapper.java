package com.neu.mapper;

import com.neu.pojo.ExMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ExMessageMapper {
    void report(@Param("message") ExMessage exMessage);

    List<ExMessage> getAllMessage();

    List<ExMessage> getDoMessage();

    void changeStatus(@Param("messageId") Integer messageId);

    List<ExMessage> getMessage(@Param("id") Integer id);

    ExMessage getStaffMessage(@Param("id") Integer messageId);


    ExMessage getOneById(@Param("messageId") Integer exMessageId);
}
