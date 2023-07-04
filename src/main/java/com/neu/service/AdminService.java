package com.neu.service;

import com.neu.dto.TestingDto;
import com.neu.pojo.Admin;

import java.util.List;
import java.util.Map;


public interface AdminService {

    List<Admin> listAdmin();

    Map<String,Object> login(Admin admin);

    void giveMessageToStaff(Integer messageId, Integer staffId);

    List<TestingDto> getAllResult();

    Admin getInfo(String token);
}
