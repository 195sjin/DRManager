package com.neu.service;

import com.neu.dto.ExMessageDto;
import com.neu.pojo.ExMessage;

import java.util.List;


public interface ExMessageService {
    void report(ExMessage exMessage);

    List<ExMessageDto> listMessage();


    List<ExMessageDto> listDoMessage();

    List<ExMessage> getMessage();

    ExMessageDto getAllMessage(Integer messageId);

}
