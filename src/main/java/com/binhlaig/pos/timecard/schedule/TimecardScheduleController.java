package com.binhlaig.pos.timecard.schedule;

import com.binhlaig.pos.shopfeature.FeatureKey;
import com.binhlaig.pos.shopfeature.ShopFeatureService;
import com.binhlaig.pos.timecard.schedule.dto.TimecardSchedulePublishRequest;
import com.binhlaig.pos.timecard.schedule.dto.TimecardSchedulePublishResponse;
import com.binhlaig.pos.timecard.schedule.dto.TimecardScheduleRequest;
import com.binhlaig.pos.timecard.schedule.dto.TimecardScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timecard/schedules")
@RequiredArgsConstructor
public class TimecardScheduleController {

    private final TimecardScheduleService scheduleService;
    private final ShopFeatureService shopFeatureService;

    @GetMapping
    public List<TimecardScheduleResponse> list(
            @RequestParam String from,
            @RequestParam String to,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireTimecardFeature(authorizationHeader);
        return scheduleService.list(from, to, authorizationHeader);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TimecardScheduleResponse create(
            @RequestBody TimecardScheduleRequest request,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireTimecardFeature(authorizationHeader);
        return scheduleService.create(request, authorizationHeader);
    }

    @PutMapping("/{id}")
    public TimecardScheduleResponse update(
            @PathVariable Long id,
            @RequestBody TimecardScheduleRequest request,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireTimecardFeature(authorizationHeader);
        return scheduleService.update(id, request, authorizationHeader);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireTimecardFeature(authorizationHeader);
        scheduleService.delete(id, authorizationHeader);
    }

    @PostMapping("/publish")
    public TimecardSchedulePublishResponse publish(
            @RequestBody TimecardSchedulePublishRequest request,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireTimecardFeature(authorizationHeader);
        return scheduleService.publish(request, authorizationHeader);
    }

    private void requireTimecardFeature(String authorizationHeader) {
        shopFeatureService.requireFeatureFromAuthorization(authorizationHeader, FeatureKey.TIMECARD);
    }
}
