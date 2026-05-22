package com.binhlaig.pos.timecard.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TimecardShiftResponse {
    private Long id;
    private Long employeeId;
    private String workDate;
    private String clockInTime;
    private String clockOutTime;
    private String note;
    private List<BreakDto> breaks;
    private Long shopId;
    private String shopCode;

    @Data
    @Builder
    public static class BreakDto {
        private Long id;
        private String startTime;
        private String endTime;
    }
}