//
//package com.binhlaig.pos.timecard.controller;
//
//import com.binhlaig.pos.timecard.dto.ClockInRequest;
//import com.binhlaig.pos.timecard.dto.TimecardShiftResponse;
//import com.binhlaig.pos.timecard.service.TimecardService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/timecard")
//@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
//public class TimecardController {
//
//    private final TimecardService timecardService;
//
//    @GetMapping("/shifts")
//    public List<TimecardShiftResponse> getShifts(
//            @RequestParam(required = false) Long employeeId,
//            @RequestParam(required = false) Long shopId
//    ) {
//        return timecardService.getShifts(employeeId, shopId);
//    }
//
//    @PostMapping("/clock-in")
//    public TimecardShiftResponse clockIn(@RequestBody ClockInRequest req) {
//        return timecardService.clockIn(req);
//    }
//
//    @PostMapping("/{shiftId}/clock-out")
//    public TimecardShiftResponse clockOut(@PathVariable Long shiftId) {
//        return timecardService.clockOut(shiftId);
//    }
//
//    @PostMapping("/{shiftId}/break/start")
//    public TimecardShiftResponse startBreak(@PathVariable Long shiftId) {
//        return timecardService.startBreak(shiftId);
//    }
//
//    @PostMapping("/{shiftId}/break/end")
//    public TimecardShiftResponse endBreak(@PathVariable Long shiftId) {
//        return timecardService.endBreak(shiftId);
//    }
//}






















package com.binhlaig.pos.timecard.controller;

import com.binhlaig.pos.timecard.dto.ClockInRequest;
import com.binhlaig.pos.timecard.dto.TimecardShiftResponse;
import com.binhlaig.pos.timecard.service.TimecardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timecard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TimecardController {

    private final TimecardService timecardService;

    @GetMapping("/shifts")
    public List<TimecardShiftResponse> getShifts(
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) Long shopId
    ) {
        return timecardService.getShifts(employeeId, shopId);
    }

    @GetMapping("/my-shifts")
    public List<TimecardShiftResponse> getMyShifts(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        return timecardService.getMyShifts(authorizationHeader);
    }

    @PostMapping("/clock-in")
    public TimecardShiftResponse clockIn(
            @RequestBody ClockInRequest req,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        return timecardService.clockIn(req, authorizationHeader);
    }

    @PostMapping("/{shiftId}/clock-out")
    public TimecardShiftResponse clockOut(@PathVariable Long shiftId) {
        return timecardService.clockOut(shiftId);
    }

    @PostMapping("/{shiftId}/break/start")
    public TimecardShiftResponse startBreak(@PathVariable Long shiftId) {
        return timecardService.startBreak(shiftId);
    }

    @PostMapping("/{shiftId}/break/end")
    public TimecardShiftResponse endBreak(@PathVariable Long shiftId) {
        return timecardService.endBreak(shiftId);
    }
}