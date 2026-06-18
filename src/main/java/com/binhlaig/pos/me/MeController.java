package com.binhlaig.pos.me;

import com.binhlaig.pos.me.dto.MeProfileResponse;
import com.binhlaig.pos.me.dto.MyPlanResponse;
import com.binhlaig.pos.me.dto.MyShopResponse;
import com.binhlaig.pos.me.dto.UpdateMyShopRequest;
import com.binhlaig.pos.me.dto.UpdateProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/me")
public class MeController {

    private final MeService meService;

    @GetMapping("/profile")
    public MeProfileResponse getProfile(Authentication authentication) {
        return meService.getProfile(authentication);
    }

    @PutMapping("/profile")
    public MeProfileResponse updateProfile(Authentication authentication, @RequestBody(required = false) UpdateProfileRequest request) {
        return meService.updateProfile(authentication, request);
    }

    @GetMapping("/shop")
    public MyShopResponse getMyShop(Authentication authentication) {
        return meService.getMyShop(authentication);
    }

    @PutMapping("/shop")
    public MyShopResponse updateMyShop(Authentication authentication, @RequestBody(required = false) UpdateMyShopRequest request) {
        return meService.updateMyShop(authentication, request);
    }

    @GetMapping("/plan")
    public MyPlanResponse getMyPlan(Authentication authentication) {
        return meService.getMyPlan(authentication);
    }
}
