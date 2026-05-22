//
//
//package com.binhlaig.pos.auth;
//
//import com.binhlaig.pos.auth.dto.AuthResponse;
//import com.binhlaig.pos.auth.dto.LoginRequest;
//import com.binhlaig.pos.auth.dto.RegisterMultipartRequest;
//import com.binhlaig.pos.auth.dto.RegisterResponse;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    private final AuthService service;
//
//    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public RegisterResponse register(
//            @RequestParam("username") String username,
//            @RequestParam("password") String password,
//            @RequestParam("role") String role,
//            @RequestParam("shopId") Long shopId,
//            @RequestParam("shopCode") String shopCode,
//            @RequestParam("shopName") String shopName,
//            @RequestParam("address") String address,
//            @RequestParam(value = "image", required = false) MultipartFile image
//    ) throws Exception {
//        var req = new RegisterMultipartRequest(
//                username,
//                password,
//                Role.valueOf(role),
//                shopId,
//                shopCode,
//                shopName,
//                address
//        );
//
//        return service.registerMultipart(req, image);
//    }
//
//    @PostMapping("/login")
//    public AuthResponse login(@RequestBody @Valid LoginRequest req) {
//        return service.login(req);
//    }
//}




















package com.binhlaig.pos.auth;

import com.binhlaig.pos.auth.dto.AuthResponse;
import com.binhlaig.pos.auth.dto.LoginRequest;
import com.binhlaig.pos.auth.dto.RegisterMultipartRequest;
import com.binhlaig.pos.auth.dto.RegisterResponse;
import com.binhlaig.pos.auth.dto.StaffLoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RegisterResponse register(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("role") String role,
            @RequestParam("shopId") Long shopId,
            @RequestParam("shopCode") String shopCode,
            @RequestParam("shopName") String shopName,
            @RequestParam("address") String address,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) throws Exception {
        var req = new RegisterMultipartRequest(
                username,
                password,
                Role.valueOf(role),
                shopId,
                shopCode,
                shopName,
                address
        );

        return service.registerMultipart(req, image);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginRequest req) {
        return service.login(req);
    }

    @PostMapping("/staff/login")
    public AuthResponse staffLogin(@RequestBody @Valid StaffLoginRequest req) {
        return service.staffLogin(req);
    }
}