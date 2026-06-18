package com.binhlaig.pos.timecard.schedule.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimecardScheduleRequest {
    private String staffId;
    private String date;
    private String startTime;
    private String endTime;
    private String role;
    private String note;
    private String status;
}
