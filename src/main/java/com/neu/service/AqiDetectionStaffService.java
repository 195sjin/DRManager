package com.neu.service;

import com.neu.pojo.AqiDetectionStaff;

import java.util.List;
import java.util.Map;


public interface AqiDetectionStaffService {
    Map<String,Object> login(AqiDetectionStaff detectionStaff);

    List<AqiDetectionStaff> listStaff();

    AqiDetectionStaff getInfo(String token);
}
