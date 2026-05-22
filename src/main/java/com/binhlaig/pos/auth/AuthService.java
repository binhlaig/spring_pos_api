//
//package com.binhlaig.pos.auth;
//
//import com.binhlaig.pos.auth.dto.AuthResponse;
//import com.binhlaig.pos.auth.dto.LoginRequest;
//import com.binhlaig.pos.auth.dto.RegisterMultipartRequest;
//import com.binhlaig.pos.auth.dto.RegisterResponse;
//import com.binhlaig.pos.auth.dto.StaffLoginRequest;
//import com.binhlaig.pos.staff.entity.Staff;
//import com.binhlaig.pos.staff.repository.StaffRepository;
//import com.binhlaig.pos.storage.FileStorageService;
//import com.binhlaig.pos.user.User;
//import com.binhlaig.pos.user.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//
//    private final UserRepository userRepository;
//    private final StaffRepository staffRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;
//    private final FileStorageService fileStorageService;
//
//    public RegisterResponse registerMultipart(RegisterMultipartRequest req, MultipartFile image) throws Exception {
//        String username = req.username() == null ? "" : req.username().trim();
//        String password = req.password() == null ? "" : req.password().trim();
//        String shopCode = req.shopCode() == null ? "" : req.shopCode().trim().toUpperCase();
//        String shopName = req.shopName() == null ? "" : req.shopName().trim();
//        String address = req.address() == null ? "" : req.address().trim();
//
//        if (username.isBlank()) {
//            throw new RuntimeException("Username is required");
//        }
//
//        if (password.isBlank() || password.length() < 8) {
//            throw new RuntimeException("Password must be at least 8 characters");
//        }
//
//        if (req.shopId() == null || req.shopId() <= 0) {
//            throw new RuntimeException("Valid shop ID is required");
//        }
//
//        if (shopCode.isBlank()) {
//            throw new RuntimeException("Shop code is required");
//        }
//
//        if (shopName.isBlank()) {
//            throw new RuntimeException("Shop name is required");
//        }
//
//        if (address.isBlank()) {
//            throw new RuntimeException("Address is required");
//        }
//
//        if (userRepository.findByUsername(username).isPresent()) {
//            throw new RuntimeException("Username already exists");
//        }
//
//        String imageUrl = null;
//        if (image != null && !image.isEmpty()) {
//            imageUrl = fileStorageService.saveAvatarImage(image);
//        }
//
//        User user = User.builder()
//                .username(username)
//                .password(passwordEncoder.encode(password))
//                .role(req.role())
//                .shopId(req.shopId())
//                .shopCode(shopCode)
//                .shopName(shopName)
//                .address(address)
//                .imageUrl(imageUrl)
//                .build();
//
//        userRepository.save(user);
//
//        return RegisterResponse.builder()
//                .message("User registered successfully")
//                .username(user.getUsername())
//                .role(user.getRole().name())
//                .shopId(user.getShopId())
//                .shopCode(user.getShopCode())
//                .shopName(user.getShopName())
//                .address(user.getAddress())
//                .imageUrl(user.getImageUrl())
//                .build();
//    }
//
//    public AuthResponse login(LoginRequest req) {
//        String username = req.username() == null ? "" : req.username().trim();
//        String password = req.password() == null ? "" : req.password().trim();
//        String requestShopCode = req.shopCode() == null ? "" : req.shopCode().trim().toUpperCase();
//
//        if (username.isBlank()) {
//            throw new RuntimeException("Username is required");
//        }
//
//        if (password.isBlank()) {
//            throw new RuntimeException("Password is required");
//        }
//
//        if (requestShopCode.isBlank()) {
//            throw new RuntimeException("Shop code is required");
//        }
//
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password)
//        );
//
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        String userShopCode = user.getShopCode() == null ? "" : user.getShopCode().trim().toUpperCase();
//
//        if (!userShopCode.equals(requestShopCode)) {
//            throw new RuntimeException("Invalid shop code");
//        }
//
//        String token = jwtService.generateToken(user);
//
//        return AuthResponse.builder()
//                .token(token)
//                .tokenType("Bearer")
//                .username(user.getUsername())
//                .role(user.getRole().name())
//                .shopId(user.getShopId())
//                .shopCode(user.getShopCode())
//                .imageUrl(user.getImageUrl())
//                .build();
//    }
//
//    public AuthResponse staffLogin(StaffLoginRequest req) {
//        String shopCode = req.getShopCode() == null ? "" : req.getShopCode().trim().toUpperCase();
//        Long staffId = req.getStaffId();
//        String password = req.getPassword() == null ? "" : req.getPassword().trim();
//
//        if (shopCode.isBlank()) {
//            throw new RuntimeException("Shop code is required");
//        }
//
//        if (staffId == null) {
//            throw new RuntimeException("Staff ID is required");
//        }
//
//        if (password.isBlank()) {
//            throw new RuntimeException("Password is required");
//        }
//
//        Staff staff = staffRepository.findByShopCodeAndStaffId(shopCode, staffId)
//                .orElseThrow(() -> new RuntimeException("Invalid shop code or staff ID"));
//
//        if (!passwordEncoder.matches(password, staff.getPassword())) {
//            throw new RuntimeException("Invalid password");
//        }
//
//        if (staff.getStatus() != null && staff.getStatus().equalsIgnoreCase("inactive")) {
//            throw new RuntimeException("Your account is inactive");
//        }
//
//        String token = jwtService.generateStaffToken(staff);
//
//        return AuthResponse.builder()
//                .token(token)
//                .tokenType("Bearer")
//                .username(staff.getFullName())
//                .role(staff.getRole())
//                .shopId(staff.getShopId())
//                .shopCode(staff.getShopCode())
//                .staffId(staff.getStaffId())
//                .imageUrl(staff.getImageUrl())
//                .build();
//    }
//
//
//}
























package com.binhlaig.pos.auth;

import com.binhlaig.pos.auth.dto.AuthResponse;
import com.binhlaig.pos.auth.dto.LoginRequest;
import com.binhlaig.pos.auth.dto.RegisterMultipartRequest;
import com.binhlaig.pos.auth.dto.RegisterResponse;
import com.binhlaig.pos.auth.dto.StaffLoginRequest;
import com.binhlaig.pos.staff.entity.Staff;
import com.binhlaig.pos.staff.repository.StaffRepository;
import com.binhlaig.pos.storage.FileStorageService;
import com.binhlaig.pos.user.User;
import com.binhlaig.pos.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final FileStorageService fileStorageService;

    public RegisterResponse registerMultipart(RegisterMultipartRequest req, MultipartFile image) throws Exception {
        String username = req.username() == null ? "" : req.username().trim();
        String password = req.password() == null ? "" : req.password().trim();
        String shopCode = req.shopCode() == null ? "" : req.shopCode().trim().toUpperCase();
        String shopName = req.shopName() == null ? "" : req.shopName().trim();
        String address = req.address() == null ? "" : req.address().trim();

        if (username.isBlank()) {
            throw new RuntimeException("Username is required");
        }

        if (password.isBlank() || password.length() < 8) {
            throw new RuntimeException("Password must be at least 8 characters");
        }

        if (req.shopId() == null || req.shopId() <= 0) {
            throw new RuntimeException("Valid shop ID is required");
        }

        if (shopCode.isBlank()) {
            throw new RuntimeException("Shop code is required");
        }

        if (shopName.isBlank()) {
            throw new RuntimeException("Shop name is required");
        }

        if (address.isBlank()) {
            throw new RuntimeException("Address is required");
        }

        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        String imageUrl = null;

        if (image != null && !image.isEmpty()) {
            imageUrl = fileStorageService.saveAvatarImage(image);
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(req.role())
                .shopId(req.shopId())
                .shopCode(shopCode)
                .shopName(shopName)
                .address(address)
                .imageUrl(imageUrl)
                .build();

        userRepository.save(user);

        return RegisterResponse.builder()
                .message("User registered successfully")
                .username(user.getUsername())
                .role(user.getRole().name())
                .shopId(user.getShopId())
                .shopCode(user.getShopCode())
                .shopName(user.getShopName())
                .address(user.getAddress())
                .imageUrl(user.getImageUrl())
                .build();
    }

    public AuthResponse login(LoginRequest req) {
        String username = req.username() == null ? "" : req.username().trim();
        String password = req.password() == null ? "" : req.password().trim();
        String requestShopCode = req.shopCode() == null ? "" : req.shopCode().trim().toUpperCase();

        if (username.isBlank()) {
            throw new RuntimeException("Username is required");
        }

        if (password.isBlank()) {
            throw new RuntimeException("Password is required");
        }

        if (requestShopCode.isBlank()) {
            throw new RuntimeException("Shop code is required");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String userShopCode = user.getShopCode() == null ? "" : user.getShopCode().trim().toUpperCase();

        if (!userShopCode.equals(requestShopCode)) {
            throw new RuntimeException("Invalid shop code");
        }

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .username(user.getUsername())
                .role(user.getRole().name())
                .shopId(user.getShopId())
                .shopCode(user.getShopCode())
                .imageUrl(user.getImageUrl())
                .build();
    }

    public AuthResponse staffLogin(StaffLoginRequest req) {
        String shopCode = req.getShopCode() == null ? "" : req.getShopCode().trim().toUpperCase();
        Long staffId = req.getStaffId();
        String password = req.getPassword() == null ? "" : req.getPassword().trim();

        if (shopCode.isBlank()) {
            throw new RuntimeException("Shop code is required");
        }

        if (staffId == null) {
            throw new RuntimeException("Staff ID is required");
        }

        if (password.isBlank()) {
            throw new RuntimeException("Password is required");
        }

        Staff staff = staffRepository.findByShopCodeAndStaffId(shopCode, staffId)
                .orElseThrow(() -> new RuntimeException("Invalid shop code or staff ID"));

        if (staff.getPassword() == null || staff.getPassword().isBlank()) {
            throw new RuntimeException("Staff password is not set");
        }

        if (!passwordEncoder.matches(password, staff.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        if (staff.getStatus() != null && staff.getStatus().equalsIgnoreCase("inactive")) {
            throw new RuntimeException("Your account is inactive");
        }

        User user = userRepository.findFirstByShopCodeIgnoreCase(shopCode)
                .orElseThrow(() -> new RuntimeException("Shop user not found for shop code: " + shopCode));

        if (user.getShopId() != null && staff.getShopId() != null && !user.getShopId().equals(staff.getShopId())) {
            throw new RuntimeException("Staff does not belong to this shop");
        }

        String token = jwtService.generateStaffToken(staff, user);

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .username(staff.getFullName())
                .role(staff.getRole())
                .shopId(staff.getShopId())
                .shopCode(staff.getShopCode())
                .staffId(staff.getStaffId())
                .imageUrl(staff.getImageUrl())
                .build();
    }
}