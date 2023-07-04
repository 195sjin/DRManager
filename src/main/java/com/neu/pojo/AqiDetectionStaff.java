package com.neu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AqiDetectionStaff{
    private Integer id;
    private String password;
    private String name;
    private String telephone;

}
