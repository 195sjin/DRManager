package com.neu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assign{
    private Integer id;
    private Integer AQIDetectionStaffId;
    private Integer ExMassageId;
    private Integer status;
}
