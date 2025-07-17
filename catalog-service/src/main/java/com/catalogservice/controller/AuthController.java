package com.catalogservice.controller;

import com.catalogservice.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
=======
import java.util.Map;

>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

<<<<<<< HEAD
    @PostMapping("/authorize")
    public ResponseEntity<?> authorize(@RequestBody Map<String, Object> body) {
        Object tokenObj = body.get("token");

        if (tokenObj == null || !(tokenObj instanceof String token) || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token is required");
        }

=======
    @GetMapping("/authorize")
    public ResponseEntity<?> authorize(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token is required");
        }
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
        try {
            // Remove Token prefix if present
            if (token.startsWith("Token ")) {
                token = token.substring(6).trim();
            }
<<<<<<< HEAD

            Object rolesObj = jwtUtil.extractRole(token);
            System.out.println("Roles from JWT: " + rolesObj);

            if (rolesObj instanceof List<?> roles) {
                for (Object r : roles) {
                    if (r != null && (r.toString().equalsIgnoreCase("admin"))) {
                        return ResponseEntity.ok(Map.of("authorized", true));
                    }
                }
            } else if (rolesObj instanceof String role) {
                if ("admin".equalsIgnoreCase(role)) {
                    return ResponseEntity.ok(Map.of("authorized", true));
                }
            }

            // If cms_admin not found
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authorized to access this resource");


=======
            String role = jwtUtil.extractRole(token);
            if ("cms_admin".equals(role)) {
                return ResponseEntity.ok(Map.of("authorized", true));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to access this resource");
            }
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}
