package com.nisum.UserService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.UserService.dto.LoginRequest;
import com.nisum.UserService.dto.SignupRequest;
import com.nisum.UserService.dto.UserResponse;
import com.nisum.UserService.exception.InvalidCredentialsException;
import com.nisum.UserService.exception.UserNotFoundException;
import com.nisum.UserService.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new com.nisum.UserService.exception.GlobalExceptionHandler())
                .build();
    }

    @Test
    void signup_shouldReturnSuccess_whenNewUser() throws Exception {
        SignupRequest req = new SignupRequest();
        req.setFirstName("John");
        req.setLastName("Doe");
        req.setEmail("x@example.com");
        req.setPassword("123");
        req.setPhoneNumber("1234567890");

        when(authService.signup(any())).thenReturn("Signup successful.");

        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Signup successful."));
    }

    @Test
    void signup_shouldReturnError_whenEmailAlreadyExists() throws Exception {
        SignupRequest req = new SignupRequest();
        req.setFirstName("John");
        req.setLastName("Doe");
        req.setEmail("x@example.com");
        req.setPassword("123");
        req.setPhoneNumber("1234567890");

        when(authService.signup(any())).thenReturn("Email already registered.");

        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Email already registered."));
    }

    @Test
    void login_shouldReturnUserResponse_whenPasswordCorrect() throws Exception {
        LoginRequest req = new LoginRequest();
        req.setEmail("test@example.com");
        req.setPassword("password");
        UserResponse userResponse = new UserResponse(1, "Test User", "test@example.com", "mock-jwt-token", Collections.singletonList("customer"));
        when(authService.login(any(LoginRequest.class))).thenReturn(userResponse);
        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.fullName").value("Test User"))
                .andExpect(jsonPath("$.token").value("mock-jwt-token"))
                .andExpect(jsonPath("$.roles[0]").value("customer"));
    }

    @Test
    void login_shouldReturnUnauthorized_whenPasswordIncorrect() throws Exception {
        LoginRequest req = new LoginRequest();
        req.setEmail("y@example.com");
        req.setPassword("wrong");

        when(authService.login(any())).thenThrow(new InvalidCredentialsException("Incorrect password."));

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Incorrect password."));
    }

    @Test
    void login_shouldReturnUnauthorized_whenUserNotFound() throws Exception {
        LoginRequest req = new LoginRequest();
        req.setEmail("not@found.com");
        req.setPassword("123");

        when(authService.login(any())).thenThrow(new UserNotFoundException("User not found."));

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("User not found."));
    }

    @Test
    void getCurrentUser_shouldReturnUserInfo_whenAuthenticated() throws Exception {
        var userInfo = new com.nisum.UserService.dto.UserBasicInfoResponse("Test User", "test@example.com", "1234567890");
        org.springframework.security.core.Authentication authentication = mock(org.springframework.security.core.Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("test@example.com");
        when(authentication.getName()).thenReturn("test@example.com");
        org.springframework.security.core.context.SecurityContext securityContext = mock(org.springframework.security.core.context.SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        org.springframework.security.core.context.SecurityContextHolder.setContext(securityContext);
        when(authService.getUserBasicInfoByEmail("test@example.com")).thenReturn(userInfo);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/user/current"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void getCurrentUser_shouldReturnUnauthorized_whenNotAuthenticated() throws Exception {
        org.springframework.security.core.Authentication authentication = mock(org.springframework.security.core.Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);
        org.springframework.security.core.context.SecurityContext securityContext = mock(org.springframework.security.core.context.SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        org.springframework.security.core.context.SecurityContextHolder.setContext(securityContext);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/user/current"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getUserBasicInfo_shouldReturnUserInfo_whenAuthenticated() throws Exception {
        var userInfo = new com.nisum.UserService.dto.UserBasicInfoResponse("Test User", "test@example.com", "1234567890");
        org.springframework.security.core.Authentication authentication = mock(org.springframework.security.core.Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("test@example.com");
        org.springframework.security.core.context.SecurityContext securityContext = mock(org.springframework.security.core.context.SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        org.springframework.security.core.context.SecurityContextHolder.setContext(securityContext);
        when(authService.getUserBasicInfoByIdWithCustomerCheck(1, true)).thenReturn(userInfo);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/user/basic-info/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void getUserBasicInfo_shouldReturnUnauthorized_whenNotAuthenticated() throws Exception {
        org.springframework.security.core.Authentication authentication = mock(org.springframework.security.core.Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);
        org.springframework.security.core.context.SecurityContext securityContext = mock(org.springframework.security.core.context.SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        org.springframework.security.core.context.SecurityContextHolder.setContext(securityContext);
        // Even if not authenticated, controller passes isAuthenticated=false to service, which may throw
        when(authService.getUserBasicInfoByIdWithCustomerCheck(2, false)).thenThrow(new com.nisum.UserService.exception.UserNotFoundException("User not found."));
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/user/basic-info/2"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getUserBasicInfo_shouldReturnNotFound_whenServiceThrows() throws Exception {
        org.springframework.security.core.Authentication authentication = mock(org.springframework.security.core.Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("test@example.com");
        org.springframework.security.core.context.SecurityContext securityContext = mock(org.springframework.security.core.context.SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        org.springframework.security.core.context.SecurityContextHolder.setContext(securityContext);
        when(authService.getUserBasicInfoByIdWithCustomerCheck(3, true)).thenThrow(new com.nisum.UserService.exception.UserNotFoundException("User not found."));
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/user/basic-info/3"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("User not found."));
    }
}
