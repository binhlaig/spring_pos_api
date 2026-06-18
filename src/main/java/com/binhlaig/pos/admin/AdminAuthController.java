package com.binhlaig.pos.admin;

import com.binhlaig.pos.admin.dto.AdminLoginRequest;
import com.binhlaig.pos.admin.dto.AdminLoginResponse;
import com.binhlaig.pos.admin.dto.AdminRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/auth")
@Slf4j
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    @Value("${app.admin.setup-key:}")
    private String setupKey;

    @Value("${app.admin.bootstrap-enabled:false}")
    private boolean bootstrapEnabled;

    @PostMapping("/login")
    public AdminLoginResponse login(@RequestBody @Valid AdminLoginRequest request) {
        return adminAuthService.login(request);
    }

    @PostMapping("/register")
    public AdminLoginResponse register(
            @RequestHeader(value = "X-Setup-Key", required = false) String setupKey,
            @RequestBody @Valid AdminRegisterRequest request
    ) {
        if (!bootstrapEnabled) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin bootstrap is disabled");
        }

        String expectedSetupKey = normalize(this.setupKey);
        String providedSetupKey = normalize(setupKey);

        log.info(
                "Admin auth request received: method=POST path=/api/admin/auth/register expectedKeyPresent={} expectedKeyLength={} requestKeyPresent={} requestKeyLength={}",
                !expectedSetupKey.isBlank(),
                expectedSetupKey.length(),
                !providedSetupKey.isBlank(),
                providedSetupKey.length()
        );

        if (expectedSetupKey.isBlank() || providedSetupKey.isBlank()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Setup key is required");
        }

        if (!expectedSetupKey.equals(providedSetupKey)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid setup key");
        }

        return adminAuthService.register(request);
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim();
    }
}
