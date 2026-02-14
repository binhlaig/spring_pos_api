package com.binhlaig.pos.auth;

import com.binhlaig.pos.auth.dto.AuthResponse;
import com.binhlaig.pos.auth.dto.LoginRequest;
import com.binhlaig.pos.auth.dto.RegisterMultipartRequest;
import com.binhlaig.pos.auth.dto.RegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public RegisterResponse register(
            @RequestPart("username") String username,
            @RequestPart("password") String password,
            @RequestPart("role") String role,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws Exception {
        var req = new RegisterMultipartRequest(username, password, Role.valueOf(role));
        return service.registerMultipart(req, image); // ✅ fixed
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginRequest req) {
        return service.login(req);
    }
}
