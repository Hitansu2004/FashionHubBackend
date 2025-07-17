package com.nisum.inventoryService.service.impl;

import com.nisum.inventoryService.dto.LoginDTO;
import com.nisum.inventoryService.dto.LoginResponseDTO;
import com.nisum.inventoryService.exception.ExternalServiceException;
import com.nisum.inventoryService.exception.InvalidLoginException;
import com.nisum.inventoryService.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    private static final String USER_SERVICE_LOGIN_URL = "http://localhost:8084/user/login"; // via API Gateway

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public LoginResponseDTO validateLogin(LoginDTO dto) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<LoginDTO> entity = new HttpEntity<>(dto, headers);

            ResponseEntity<LoginResponseDTO> response = restTemplate.postForEntity(
                    USER_SERVICE_LOGIN_URL,
                    entity,
                    LoginResponseDTO.class
            );

            return response.getBody();

        } catch (HttpClientErrorException.Unauthorized ex) {
//            log.error("Unauthorized login attempt: {}", ex.getMessage());
            throw new InvalidLoginException("Invalid email or password.");
        } catch (HttpClientErrorException ex) {
//            log.error("Client error during login: {}", ex.getMessage());
            throw new ExternalServiceException("Login failed due to client error.");
        } catch (Exception e) {
//            log.error("Unexpected error during login: {}", e.getMessage());
            throw new ExternalServiceException("Login failed due to unexpected error.");
        }
    }
}
