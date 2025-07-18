//package com.nisum.inventoryService.controller;
//
//import com.nisum.inventoryService.dto.LoginDTO;
//import com.nisum.inventoryService.exception.ExternalServiceException;
//import com.nisum.inventoryService.exception.InvalidLoginException;
//import com.nisum.inventoryService.service.LoginService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/public/")
//public class LoginController {
//
//    @Autowired
//    private LoginService loginService;
//
//
//    @PostMapping("/login")
//    public ResponseEntity<?> validateAndGenerateLogin(@RequestBody LoginDTO dto){
//        try {
//            if (dto == null || dto.getEmail() == null || dto.getPassword() == null) {
//                return ResponseEntity.badRequest().body("Username or password must not be null");
//            }
//
//            System.out.println("Login request received for user: " + dto.getEmail());
//
//            String token = loginService.validateLogin(dto);
//            return ResponseEntity.ok().body(token);
//
//        } catch (InvalidLoginException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//        } catch (ExternalServiceException e) {
//            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
//        }
//    }
//}



//
//package com.nisum.inventoryService.controller;
//
//import com.nisum.inventoryService.dto.LoginDTO;
//import com.nisum.inventoryService.dto.LoginResponseDTO;
//import com.nisum.inventoryService.service.LoginService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/public")
//@Slf4j
//public class LoginController {
//
//    @Autowired
//    private LoginService loginService;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> validateAndGenerateLogin(@RequestBody LoginDTO dto) {
//       log.info("Login request received for user: {}", dto.getEmail());
//            // ✅ Return structured response instead of raw token string
//            LoginResponseDTO responseDTO = loginService.validateLogin(dto);
//            if (null != responseDTO){
//                log.info("Login successful for user: {}", dto.getEmail());
//                return ResponseEntity.ok(responseDTO);
//            } else {
//                log.error("Login failed for user: {}", dto.getEmail());
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//            }
//
//    }
//}



package com.nisum.inventoryService.controller;

import com.nisum.inventoryService.dto.LoginDTO;
import com.nisum.inventoryService.dto.LoginResponseDTO;
import com.nisum.inventoryService.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> validateAndGenerateLogin(@RequestBody LoginDTO dto) {
        if (dto == null || dto.getEmail() == null || dto.getPassword() == null) {
            log.warn("Login failed: Missing email or password");
            return ResponseEntity.badRequest().body("Email and password must not be null");
        }

        log.info("Login request received for user: {}", dto.getEmail());

        try {
            LoginResponseDTO responseDTO = loginService.validateLogin(dto);

            if (responseDTO != null) {
                log.info("Login successful for user: {}", dto.getEmail());
                return ResponseEntity.ok(responseDTO);
            } else {
                log.warn("Login failed for user: {}", dto.getEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e) {
            log.error("Unexpected error during login for user: {} - {}", dto.getEmail(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error occurred. Please try again.");
        }
    }
}
