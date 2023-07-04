package com.neu.service.impl;

import com.neu.dto.ExMessageDto;
import com.neu.mapper.AqiDetectionStaffMapper;
import com.neu.mapper.AssignMapper;
import com.neu.mapper.ExMessageMapper;
import com.neu.mapper.PublicSupervisorMapper;
import com.neu.pojo.AqiDetectionStaff;
import com.neu.pojo.Assign;
import com.neu.pojo.ExMessage;
import com.neu.pojo.PublicSupervisor;
import com.neu.service.ExMessageService;
import com.neu.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExMessageServiceImpl implements ExMessageService {

    @Resource
    private ExMessageMapper mapper;
    @Resource
    private PublicSupervisorMapper publicSupervisorMapper;
    @Resource
    private AssignMapper assignMapper;
    @Resource
    private AqiDetectionStaffMapper aqiDetectionStaffMapper;
    @Resource
    private HttpServletRequest request;
    @Resource
    private JwtUtil jwtUtil;

    //监督员进行举报
    @Override
    public void report(ExMessage exMessage) {
        String token = request.getHeader("Token");
        PublicSupervisor supervisor = jwtUtil.parseToken(token, PublicSupervisor.class);
        exMessage.setSupervisorId(supervisor.getId());
        exMessage.setUpdateTime(new Date());
        exMessage.setStatus(0);
        mapper.report(exMessage);
    }

    //获取委派给每个检测员的异常信息
    @Override
    @Transactional
    public List<ExMessageDto> listMessage() {
        //从exmessage表里面获取status=1的数据，也就是已经指派的数据
       List<ExMessage> messages = mapper.getAllMessage();
       if (messages == null){
           //也就是说全部的异常事件都还没有分发给任何一个检测员
           return null;
       }
       //已经分发下去了
        List<ExMessageDto> messagesDtoList = messages.stream().map((item) -> {

            ExMessageDto exMessageDto = new ExMessageDto();
            BeanUtils.copyProperties(item,exMessageDto);

            //根据举报信息里面的举报人id去举报人表里面查找
            PublicSupervisor publicSupervisor = publicSupervisorMapper.getPublicById(item.getSupervisorId());

            //获取该举报信息是不是被检测完
             Assign assign = assignMapper.getAssignByExMessageId(item.getId());
             exMessageDto.setStatus(assign.getStatus());

            //根据举报信息里面的id去指派表里面查找检测员id
            List<Integer> staffIds = assignMapper.getStaffIdByMessageId(item.getId());

            //根据检测员id去检测员表里面查找检测员姓名
            List<String> staffNames = new ArrayList<>();
            for (Integer staffId:staffIds) {
                AqiDetectionStaff staff = aqiDetectionStaffMapper.getStaffById(staffId);
                staffNames.add(staff.getName());
            }

            exMessageDto.setStaffName(staffNames);
            exMessageDto.setPublicName(publicSupervisor.getName());

            return exMessageDto;
        }).collect(Collectors.toList());



        return messagesDtoList;

    }

    //把全部举报信息回显给管理员
    @Override
    @Transactional
    public List<ExMessageDto> listDoMessage() {
        List<ExMessage> messages = mapper.getDoMessage();
        if (messages == null){
            //没有异常信息
            return null;
        }

        //有异常信息肯定就有举报人
        List<ExMessageDto> messagesDtoList = messages.stream().map((item) -> {

            ExMessageDto exMessageDto = new ExMessageDto();
            BeanUtils.copyProperties(item,exMessageDto);

            /*Assign assign = assignMapper.getAssignByExMessageId(item.getId());
            exMessageDto.setStatus(assign.getStatus());*/

            //根据举报信息里面的举报人id去举报人表里面查找
            PublicSupervisor publicSupervisor = publicSupervisorMapper.getPublicById(item.getSupervisorId());

            exMessageDto.setPublicName(publicSupervisor.getName());

            return exMessageDto;
        }).collect(Collectors.toList());

        return messagesDtoList;
    }

    //获取监督者的举报信息
    @Override
    public List<ExMessage> getMessage() {

        String token = request.getHeader("Token");
        PublicSupervisor supervisor = jwtUtil.parseToken(token, PublicSupervisor.class);

        List<ExMessage> messages = mapper.getMessage(supervisor.getId());
        if (messages == null){
            return null;
        }
        return messages;
    }

    //显示给检测者的举报信息
    @Override
    public ExMessageDto getAllMessage(Integer messageId) {
        //根据事件id去事件表里面查找事件
        ExMessage messages = mapper.getStaffMessage(messageId);

        ExMessageDto exMessageDto = new ExMessageDto();

        BeanUtils.copyProperties(messages,exMessageDto);

        Assign assign = assignMapper.getAssignByExMessageId(messageId);
        exMessageDto.setStatus(assign.getStatus());

        //根据举报信息里面的举报人id去举报人表里面查找
        PublicSupervisor publicSupervisor = publicSupervisorMapper.getPublicById(messages.getSupervisorId());

        exMessageDto.setPublicName(publicSupervisor.getName());

        return exMessageDto;
    }
}
