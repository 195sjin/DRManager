package com.neu.service.impl;

import com.neu.mapper.AqiDetectionStaffMapper;
import com.neu.pojo.AqiDetectionStaff;
import com.neu.service.AqiDetectionStaffService;
import com.neu.util.JwtUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AqiDetectionStaffServiceImpl implements AqiDetectionStaffService {

    @Resource
    private AqiDetectionStaffMapper mapper;
    @Resource
    private JwtUtil jwtUtil;


    @Override
    public Map<String,Object> login(AqiDetectionStaff detectionStaff) {
        AqiDetectionStaff staff = mapper.getUserById(detectionStaff);
        if (staff != null){
            //生成token
            staff.setPassword(null);
            String token = jwtUtil.createToken(staff);
            //返回数据
            Map<String,Object> data = new HashMap<>();
            data.put("token",token);
            return data;
        }
        return null;
    }

    @Override
    public List<AqiDetectionStaff> listStaff() {
        return mapper.getAllStaff();
    }

    @Override
    public AqiDetectionStaff getInfo(String token) {
        AqiDetectionStaff staff = jwtUtil.parseToken(token, AqiDetectionStaff.class);
        if (staff != null){
            return staff;
        }
        return null;
    }

}
