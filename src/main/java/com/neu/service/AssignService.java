package com.neu.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface AssignService {
    List<Integer> getMessageId(Integer id);

    List<Integer> getDoMessageId(Integer id);
}
