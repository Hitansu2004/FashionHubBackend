package com.orderservice.sprint4.service;

import com.orderservice.sprint4.dto.LoginDTO;
import com.orderservice.sprint4.dto.LoginResponseDTO;
import com.orderservice.sprint4.exception.ExternalServiceException;
import com.orderservice.sprint4.exception.InvalidLoginException;
import com.orderservice.sprint4.service.impl.LoginServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.web.client.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginServiceImplTest {

    @InjectMocks
    private LoginServiceImpl loginService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginService = new LoginServiceImpl();
        loginService.restTemplate = restTemplate;
        loginService.LOGIN_SERVICE_LOGIN_VALIDATION_URL = "http://fake-login-service/api/login";
    }

    @Test
    void testValidateLogin_successfulLogin_returnsToken() {
        LoginDTO dto = new LoginDTO();
        dto.setEmail("user");
        dto.setPassword("password");
        LoginResponseDTO responseBody = new LoginResponseDTO();
        responseBody.setToken("fake-jwt-token");

        ResponseEntity<LoginResponseDTO> responseEntity =
                new ResponseEntity<>(responseBody, HttpStatus.OK);

        when(restTemplate.postForEntity(anyString(), eq(dto), eq(LoginResponseDTO.class)))
                .thenReturn(responseEntity);

        String token = loginService.validateLogin(dto);
        assertEquals("fake-jwt-token", token);
    }


    @Test
    void testValidateLogin_httpClientError_throwsInvalidLoginException() {
        LoginDTO dto = new LoginDTO();
        dto.setEmail("user");
        dto.setPassword("password");

        when(restTemplate.postForEntity(anyString(), eq(dto), eq(LoginResponseDTO.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Unauthorized"));

        InvalidLoginException exception = assertThrows(InvalidLoginException.class,
                () -> loginService.validateLogin(dto));

        assertTrue(exception.getMessage().contains("Invalid credentials"));
    }

    @Test
    void testValidateLogin_serverError_throwsExternalServiceException() {
        LoginDTO dto = new LoginDTO();
        dto.setEmail("user");
        dto.setPassword("password");

        when(restTemplate.postForEntity(anyString(), eq(dto), eq(LoginResponseDTO.class)))
                .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error"));

        ExternalServiceException exception = assertThrows(ExternalServiceException.class,
                () -> loginService.validateLogin(dto));

        assertTrue(exception.getMessage().contains("Login service failed"));
    }

    @Test
    void testValidateLogin_connectionIssue_throwsExternalServiceException() {
        LoginDTO dto = new LoginDTO();
        dto.setEmail("user");
        dto.setPassword("password");

        when(restTemplate.postForEntity(anyString(), eq(dto), eq(LoginResponseDTO.class)))
                .thenThrow(new ResourceAccessException("Connection refused"));

        ExternalServiceException exception = assertThrows(ExternalServiceException.class,
                () -> loginService.validateLogin(dto));

        assertTrue(exception.getMessage().contains("Unable to reach login service"));
    }

    @Test
    void testValidateLogin_unknownError_throwsExternalServiceException() {
        LoginDTO dto = new LoginDTO();
        dto.setEmail("user");
        dto.setPassword("password");

        when(restTemplate.postForEntity(anyString(), eq(dto), eq(LoginResponseDTO.class)))
                .thenThrow(new RuntimeException("Unexpected"));

        ExternalServiceException exception = assertThrows(ExternalServiceException.class,
                () -> loginService.validateLogin(dto));

        assertTrue(exception.getMessage().contains("Unexpected error during login"));
    }
}
