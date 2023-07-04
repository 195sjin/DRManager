package com.neu.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Testing{
    private Integer id;
    @JsonProperty(value = "AQIDetectionStaffId")
    private Integer AQIDetectionStaffId;
    @JsonProperty(value = "ExMessageId")
    private Integer ExMessageId;
    private Integer AQILevel;
    @JsonProperty(value = "PM")
    private Integer PM;
    @JsonProperty(value = "SO2")
    private Integer SO2;
    @JsonProperty(value = "CO")
    private Integer CO;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

}
