package com.neu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin{
    private Integer id;
    private String password;
    private String name;
    private String telephone;
}
