package com.binhlaig.pos.auth;

import com.binhlaig.pos.staff.entity.Staff;
import com.binhlaig.pos.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "binhlaigposbinhlaigposbinhlaigpos123456";

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Long extractShopId(String token) {
        Object shopId = extractAllClaims(token).get("shopId");
        return toLong(shopId);
    }

    public String extractShopCode(String token) {
        Object shopCode = extractAllClaims(token).get("shopCode");
        return shopCode != null ? shopCode.toString() : null;
    }

    public String extractRole(String token) {
        Object role = extractAllClaims(token).get("role");
        return role != null ? role.toString() : null;
    }

    public Long extractStaffId(String token) {
        Object staffId = extractAllClaims(token).get("staffId");
        return toLong(staffId);
    }

    public String extractTokenType(String token) {
        Object type = extractAllClaims(token).get("type");
        return type != null ? type.toString() : null;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        claims.put("shopId", user.getShopId());
        claims.put("shopCode", user.getShopCode());
        claims.put("type", "USER");

        return createToken(claims, user.getUsername());
    }

    /**
     * Do not use this method anymore.
     * Staff token subject must be users.username, not staff.staffId.
     */
    public String generateStaffToken(Staff staff) {
        throw new RuntimeException(
                "Wrong token generator used: generateStaffToken(staff). Use generateStaffToken(staff, user)."
        );
    }

    public String generateStaffToken(Staff staff, User user) {
        if (staff == null) {
            throw new RuntimeException("Staff is required to generate staff token");
        }

        if (user == null || user.getUsername() == null || user.getUsername().isBlank()) {
            throw new RuntimeException("User username is required to generate staff token");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", staff.getRole());
        claims.put("shopId", staff.getShopId());
        claims.put("shopCode", staff.getShopCode());
        claims.put("staffId", staff.getStaffId());
        claims.put("type", "STAFF");

        return createToken(claims, user.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, User user) {
        final String username = extractUsername(token);

        return username != null
                && username.equals(user.getUsername())
                && !isTokenExpired(token);
    }

    public boolean isStaffTokenValid(String token, Staff staff) {
        final Long staffId = extractStaffId(token);
        final String tokenType = extractTokenType(token);

        return staffId != null
                && staff.getStaffId() != null
                && staffId.equals(staff.getStaffId())
                && "STAFF".equals(tokenType)
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration != null && expiration.before(new Date());
    }

    private Long toLong(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Integer) {
            return ((Integer) value).longValue();
        }

        if (value instanceof Long) {
            return (Long) value;
        }

        if (value instanceof Number) {
            return ((Number) value).longValue();
        }

        return Long.parseLong(value.toString());
    }
}