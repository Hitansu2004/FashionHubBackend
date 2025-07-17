package com.nisum.inventoryService.service;

import com.nisum.inventoryService.dto.LoginDTO;
import com.nisum.inventoryService.dto.LoginResponseDTO;

public interface LoginService {
    LoginResponseDTO validateLogin(LoginDTO dto);
}
