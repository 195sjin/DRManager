package com.neu.service.impl;

import com.neu.dto.TestingDto;
import com.neu.mapper.*;
import com.neu.pojo.*;
import com.neu.service.AdminService;
import com.neu.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;

    @Resource
    private AssignMapper assignMapper;

    @Resource
    private ExMessageMapper exMessageMapper;

    @Resource
    private TestingMapper testingMapper;

    @Resource
    private PublicSupervisorMapper publicSupervisorMapper;

    @Resource
    private AqiDetectionStaffMapper aqiDetectionStaffMapper;

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public List<Admin> listAdmin() {

        List<Admin> admins = adminMapper.listAdmin();

        return admins;
    }

    @Override
    public Map<String,Object> login(Admin admin) {

        Admin adminById = adminMapper.getAdminById(admin.getId(),admin.getPassword());
        if (adminById != null){
            //生成token
            adminById.setPassword(null);
            String token = jwtUtil.createToken(adminById);
            //返回数据
            Map<String,Object> data = new HashMap<>();
            data.put("token",token);
            return data;

        }
        return null;
    }

    @Override
    @Transactional
    public void giveMessageToStaff(Integer messageId, Integer staffId) {
        //更改指派表里面的数据，把举报信息与检测员关联起来
        assignMapper.giveAssign(messageId,staffId);
        //更改举报信息表里面的数据，把该举报信息的status设置为1，代表已经指派给检测员
        exMessageMapper.changeStatus(messageId);

    }

    @Override
    @Transactional
    public List<TestingDto> getAllResult() {
        //去testing表里面查询所有的数据，封装为集合
        List<Testing> testingList = testingMapper.getAllTest();
        if (testingList == null){
            //检测表里没数据，也就是暂时没有监测信息
            return null;
        }

        //有监测信息

        //使用dto
        List<TestingDto> testingDtoList = null;

        testingDtoList = testingList.stream().map((item) -> {
            TestingDto testingDto = new TestingDto();

            BeanUtils.copyProperties(item,testingDto);

            //获取异常信息的id，根据它去异常信息表里面查找异常类
            //肯定不为空
            ExMessage exMessage = exMessageMapper.getOneById(item.getExMessageId());
            testingDto.setExMessage(exMessage);

            //获取异常信息提供者的姓名以及电话
            //肯定也不为空
            PublicSupervisor supervisor = publicSupervisorMapper.getPublicById(exMessage.getSupervisorId());
            testingDto.setPublicName(supervisor.getName());
            testingDto.setPublicPhone(supervisor.getTelephone());

            //获取异常信息检测员的姓名以及电话
            //也不为空
            AqiDetectionStaff aqiDetectionStaff = aqiDetectionStaffMapper.getStaffById(item.getAQIDetectionStaffId());
            testingDto.setStaffName(aqiDetectionStaff.getName());
            testingDto.setStaffPhone(aqiDetectionStaff.getTelephone());

            return testingDto;
                }).collect(Collectors.toList());

        return testingDtoList;
    }

    @Override
    public Admin getInfo(String token) {
        Admin admin = jwtUtil.parseToken(token, Admin.class);
        if (admin != null){
            return admin;
        }
        return null;
    }
}
