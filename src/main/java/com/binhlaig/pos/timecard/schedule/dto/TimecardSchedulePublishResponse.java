package com.binhlaig.pos.timecard.schedule.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimecardSchedulePublishResponse {
    private int publishedCount;
    private String from;
    private String to;
}
