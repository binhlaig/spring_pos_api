package com.binhlaig.pos.timecard.schedule.dto;

import com.binhlaig.pos.timecard.schedule.TimecardSchedule;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimecardScheduleResponse {
    private Long id;
    private String staffId;
    private String staffName;
    private String date;
    private String startTime;
    private String endTime;
    private String role;
    private String note;
    private String status;

    public static TimecardScheduleResponse from(TimecardSchedule schedule) {
        return TimecardScheduleResponse.builder()
                .id(schedule.getId())
                .staffId(schedule.getStaffId())
                .staffName(schedule.getStaffName())
                .date(schedule.getDate() == null ? null : schedule.getDate().toString())
                .startTime(schedule.getStartTime() == null ? null : schedule.getStartTime().toString())
                .endTime(schedule.getEndTime() == null ? null : schedule.getEndTime().toString())
                .role(schedule.getRole())
                .note(schedule.getNote())
                .status(schedule.getStatus() == null ? null : schedule.getStatus().name())
                .build();
    }
}
