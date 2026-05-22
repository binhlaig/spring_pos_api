

package com.binhlaig.pos.staff.service;

import com.binhlaig.pos.staff.dto.StaffRequest;
import com.binhlaig.pos.staff.dto.StaffResponse;

import java.util.List;

public interface StaffService {

    StaffResponse createStaff(StaffRequest request, String authorizationHeader);

    StaffResponse updateStaff(Long id, StaffRequest request, String authorizationHeader);

    StaffResponse getStaffById(Long id, String authorizationHeader);

    StaffResponse getStaffByStaffId(Long staffId, String authorizationHeader);

    List<StaffResponse> getAllStaff(String authorizationHeader);

    void deleteStaff(Long id, String authorizationHeader);
}