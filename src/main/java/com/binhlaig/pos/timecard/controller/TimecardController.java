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

import com.binhlaig.pos.shopfeature.FeatureKey;
import com.binhlaig.pos.shopfeature.ShopFeatureService;
import com.binhlaig.pos.staff.dto.StaffResponse;
import com.binhlaig.pos.staff.service.StaffService;
import com.binhlaig.pos.timecard.dto.ClockInRequest;
import com.binhlaig.pos.timecard.dto.TimecardShiftResponse;
import com.binhlaig.pos.timecard.service.TimecardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timecard")
@RequiredArgsConstructor
public class TimecardController {

    private final TimecardService timecardService;
    private final StaffService staffService;
    private final ShopFeatureService shopFeatureService;

    @GetMapping("/staff")
    public List<StaffResponse> getTimecardStaff(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireTimecardFeature(authorizationHeader);
        return staffService.getAllStaff(authorizationHeader);
    }

    @GetMapping("/staff/by-staff-id/{staffId}")
    public StaffResponse getTimecardStaffByStaffId(
            @PathVariable Long staffId,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireTimecardFeature(authorizationHeader);
        return staffService.getStaffByStaffId(staffId, authorizationHeader);
    }

    @GetMapping("/shifts")
    public List<TimecardShiftResponse> getShifts(
            @RequestParam(required = false) Long employeeId,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireTimecardFeature(authorizationHeader);
        return timecardService.getShifts(employeeId, authorizationHeader);
    }

    @GetMapping("/my-shifts")
    public List<TimecardShiftResponse> getMyShifts(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireTimecardFeature(authorizationHeader);
        return timecardService.getMyShifts(authorizationHeader);
    }

    @PostMapping("/clock-in")
    public TimecardShiftResponse clockIn(
            @RequestBody ClockInRequest req,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireTimecardFeature(authorizationHeader);
        return timecardService.clockIn(req, authorizationHeader);
    }

    @PostMapping("/{shiftId}/clock-out")
    public TimecardShiftResponse clockOut(
            @PathVariable Long shiftId,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireTimecardFeature(authorizationHeader);
        return timecardService.clockOut(shiftId, authorizationHeader);
    }

    @PostMapping("/{shiftId}/break/start")
    public TimecardShiftResponse startBreak(
            @PathVariable Long shiftId,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireTimecardFeature(authorizationHeader);
        return timecardService.startBreak(shiftId, authorizationHeader);
    }

    @PostMapping("/{shiftId}/break/end")
    public TimecardShiftResponse endBreak(
            @PathVariable Long shiftId,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        requireTimecardFeature(authorizationHeader);
        return timecardService.endBreak(shiftId, authorizationHeader);
    }

    private void requireTimecardFeature(String authorizationHeader) {
        shopFeatureService.requireFeatureFromAuthorization(authorizationHeader, FeatureKey.TIMECARD);
    }
}
