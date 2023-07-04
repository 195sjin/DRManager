package com.neu.service.impl;

import com.neu.dto.TestingDto;
import com.neu.mapper.AssignMapper;
import com.neu.mapper.ExMessageMapper;
import com.neu.mapper.PublicSupervisorMapper;
import com.neu.mapper.TestingMapper;
import com.neu.pojo.ExMessage;
import com.neu.pojo.PublicSupervisor;
import com.neu.pojo.Testing;
import com.neu.service.TestingService;
import com.neu.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class TestingServiceImpl implements TestingService {

    @Resource
    private TestingMapper testingMapper;
    @Resource
    private AssignMapper assignMapper;
    @Resource
    private HttpServletRequest request;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private ExMessageMapper exMessageMapper;
    @Resource
    private PublicSupervisorMapper publicSupervisorMapper;


    @Override
    @Transactional
    public void record(Testing testing) {
        testingMapper.record(testing);
        //把assign表里面的status设为1
        assignMapper.setStatus(testing.getExMessageId());
    }

    @Override
    public TestingDto getResultByExMessageId(Integer messageId) {

        Testing testing = testingMapper.getAllByExId(messageId);

        //使用dto
        TestingDto testingDto = new TestingDto();

        BeanUtils.copyProperties(testing,testingDto);

        //获取异常信息的id，根据它去异常信息表里面查找异常类
        //肯定不为空
        ExMessage exMessage = exMessageMapper.getOneById(testing.getExMessageId());
        testingDto.setExMessage(exMessage);

        //获取异常信息提供者的姓名以及电话
        //肯定也不为空
        PublicSupervisor supervisor = publicSupervisorMapper.getPublicById(exMessage.getSupervisorId());
        testingDto.setPublicName(supervisor.getName());
        testingDto.setPublicPhone(supervisor.getTelephone());

        return testingDto;

    }
}
