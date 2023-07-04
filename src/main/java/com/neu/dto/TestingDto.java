package com.neu.dto;

import com.neu.pojo.ExMessage;
import com.neu.pojo.Testing;
import lombok.Data;


@Data
public class TestingDto extends Testing {

    private ExMessage exMessage;

    private String staffName;
    private String staffPhone;
    private String publicName;
    private String publicPhone;
}
