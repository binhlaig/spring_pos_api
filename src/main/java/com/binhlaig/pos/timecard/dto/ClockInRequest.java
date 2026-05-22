package com.binhlaig.pos.timecard.dto;

import lombok.Data;

@Data
public class ClockInRequest {
    private Long employeeId;
    private String note;
}