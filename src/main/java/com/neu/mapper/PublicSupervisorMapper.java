package com.neu.mapper;

import com.neu.pojo.PublicSupervisor;
import org.apache.ibatis.annotations.Param;


public interface PublicSupervisorMapper {
    public PublicSupervisor getUserByPhone(PublicSupervisor publicSupervisor);
    public void insertUser(PublicSupervisor publicSupervisor);


    PublicSupervisor getPublicById(@Param("supervisorId") Integer supervisorId);
}
