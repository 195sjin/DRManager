package com.neu.mapper;

import com.neu.pojo.Assign;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssignMapper {
    void giveAssign(@Param("messageId") Integer messageId, @Param("staffId") Integer staffId);

    List<Integer> getStaffIdByMessageId(@Param("id") Integer id);

    List<Integer> getMessageIds(@Param("id") Integer id);

    List<Integer> getDoMessageIds(@Param("id") Integer id);

    void setStatus(@Param("id")Integer exMessageId);

    Assign getAssignByExMessageId(@Param("messageId") Integer id);
}
