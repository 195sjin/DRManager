package com.neu.mapper;

import com.neu.pojo.Admin;

import java.util.List;

public interface AdminMapper {
    public List<Admin> listAdmin();

    Admin getAdminById(Integer id,String password);
}
