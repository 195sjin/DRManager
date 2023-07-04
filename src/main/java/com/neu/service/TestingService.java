package com.neu.service;

import com.neu.dto.TestingDto;
import com.neu.pojo.Testing;


public interface TestingService {
    void record(Testing testing);

    TestingDto getResultByExMessageId(Integer messageId);
}
