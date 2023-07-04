package com.neu.service.impl;

import com.neu.mapper.AssignMapper;
import com.neu.service.AssignService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class AssignServiceImpl implements AssignService {

    @Resource
    private AssignMapper assignMapper;

    @Override
    public List<Integer> getMessageId(Integer id) {
        List<Integer> messageIds = assignMapper.getMessageIds(id);
        if (messageIds == null){
            //指派表里面没有自己还没完成的任务
            return null;
        }

        return messageIds;
    }

    @Override
    public List<Integer> getDoMessageId(Integer id) {
        List<Integer> messageIds = assignMapper.getDoMessageIds(id);
        if (messageIds == null){
            return null;
        }
        return messageIds;

    }
}
