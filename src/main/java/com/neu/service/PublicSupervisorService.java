package com.neu.service;

import com.neu.pojo.PublicSupervisor;

import java.util.Map;

public interface PublicSupervisorService {
    Map<String,Object> login(PublicSupervisor publicSupervisor);

    void register(PublicSupervisor publicSupervisor);

    PublicSupervisor getInfo(String token);
}
