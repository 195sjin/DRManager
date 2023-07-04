package com.neu.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExMessage{
    private Integer id;
    private Integer supervisorId;
    private String address;
    private String description;
    @JsonProperty(value = "vAQILevel")
    private Integer vAQILevel;
    private Integer status;
    private Date updateTime;
}
