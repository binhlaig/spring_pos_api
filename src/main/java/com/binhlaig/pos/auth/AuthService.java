//package com.binhlaig.pos.auth;
//
//import com.binhlaig.pos.auth.dto.*;
//import com.binhlaig.pos.auth.jwt.JwtService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.*;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Set;
//
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//
//    private final UserRepository repo;
//    private final PasswordEncoder encoder;
//    private final AuthenticationManager authManager;
//    private final JwtService jwtService;
//
//    @Transactional
//    public void register(RegisterRequest req) {
//        if (repo.existsByUsername(req.username())) {
//            throw new IllegalArgumentException("Username already exists");
//        }
//
//        var user = AppUser.builder()
//                .username(req.username())
//                .passwordHash(encoder.encode(req.password()))
//                .roles(Set.of(Role.ADMIN)) // default: ADMIN (နောက်မှ cashier create API လုပ်မယ်)
//                .active(true)
//                .build();
//
//        repo.save(user);
//    }
//
//
//
//
//    public AuthResponse login(LoginRequest req) {
//        authManager.authenticate(
//                new UsernamePasswordAuthenticationToken(req.username(), req.password())
//        );
//
//        var user = repo.findByUsername(req.username())
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//        String token = jwtService.generate(user.getUsername(), user.getRoles());
//        return new AuthResponse(token, "Bearer");
//    }
//}
//
//



package com.binhlaig.pos.auth;

import com.binhlaig.pos.auth.dto.*;
import com.binhlaig.pos.auth.jwt.JwtService;
import com.binhlaig.pos.common.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    // ✅ NEW: for avatar upload
    private final UploadService uploadService;

    /**
     * (Old) JSON register: keep if you still want JSON register later.
     */
    @Transactional
    public void register(RegisterRequest req) {
        if (repo.existsByUsername(req.username())) {
            throw new IllegalArgumentException("Username already exists");
        }

        var user = AppUser.builder()
                .username(req.username())
                .passwordHash(encoder.encode(req.password()))
                .roles(Set.of(Role.ADMIN))
                .active(true)
                .build();

        repo.save(user);
    }

    /**
     * ✅ NEW: Multipart register (FormData) for frontend
     * accepts: username, password, role, image(optional)
     * returns: access_token + username + role + avatarPath
     */
    @Transactional
    public RegisterResponse registerMultipart(RegisterMultipartRequest req, MultipartFile image) throws IOException {
        if (repo.existsByUsername(req.username())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Save avatar if provided
        String avatarPath = uploadService.saveAvatar(image);

        var user = AppUser.builder()
                .username(req.username())
                .passwordHash(encoder.encode(req.password()))
                .roles(Set.of(req.role()))       // ✅ role from frontend
                .active(true)
                .avatarPath(avatarPath)          // ✅ need field in AppUser
                .build();

        repo.save(user);

        String token = jwtService.generate(user.getUsername(), user.getRoles());

        return new RegisterResponse(
                token,
                user.getUsername(),
                req.role().name(),
                avatarPath
        );
    }

    public AuthResponse login(LoginRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password())
        );

        var user = repo.findByUsername(req.username())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String token = jwtService.generate(user.getUsername(), user.getRoles());
        return new AuthResponse(token, "Bearer");
    }
}
