package com.neu.service.impl;

import com.neu.mapper.PublicSupervisorMapper;
import com.neu.pojo.PublicSupervisor;
import com.neu.service.PublicSupervisorService;
import com.neu.util.JwtUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class PublicSupervisorServiceImpl implements PublicSupervisorService {

    @Resource
    private PublicSupervisorMapper mapper;
    @Resource
    private JwtUtil jwtUtil;

    @Override
    public Map<String,Object> login(PublicSupervisor publicSupervisor) {
        //去数据库里面查找数据
        PublicSupervisor supervisor = mapper.getUserByPhone(publicSupervisor);
        if (supervisor != null){
            //生成token
            supervisor.setPassword(null);
            String token = jwtUtil.createToken(supervisor);
            //返回数据
            Map<String,Object> data = new HashMap<>();
            data.put("token",token);
            return data;
        }
        return null;
    }

    @Override
    public void register(PublicSupervisor publicSupervisor) {
        //去数据库里面添加一条数据
        mapper.insertUser(publicSupervisor);
    }

    @Override
    public PublicSupervisor getInfo(String token) {
        PublicSupervisor supervisor = jwtUtil.parseToken(token, PublicSupervisor.class);
        if (supervisor != null){
            return supervisor;
        }
        return null;
    }
}
