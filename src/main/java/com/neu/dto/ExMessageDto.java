package com.neu.dto;

import com.neu.pojo.ExMessage;
import lombok.Data;

import java.util.List;


@Data
public class ExMessageDto extends ExMessage {
    private String publicName;
    private List<String> staffName;
    private Integer status;
}
