package com.binhlaig.pos.timecard.schedule.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimecardSchedulePublishRequest {
    private String from;
    private String to;
}
