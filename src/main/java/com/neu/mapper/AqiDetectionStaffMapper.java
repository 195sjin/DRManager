package com.neu.mapper;

import com.neu.pojo.AqiDetectionStaff;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface AqiDetectionStaffMapper {
    AqiDetectionStaff getUserById(AqiDetectionStaff detectionStaff);

    List<AqiDetectionStaff> getAllStaff();

    AqiDetectionStaff getStaffById(@Param("id") Integer staffId);
}
