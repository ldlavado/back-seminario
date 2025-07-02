package com.troyadevclub.integraservicios.service;

import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.dto.auth.LoginRequestDTO;
import com.troyadevclub.integraservicios.dto.auth.LoginResponseDTO;
import com.troyadevclub.integraservicios.dto.auth.RegisterRequestDTO;

public interface AuthService {

    public APIResponseDTO<LoginResponseDTO> loginUser(LoginRequestDTO request, String trace);

    public APIResponseDTO<Object> registerUser(RegisterRequestDTO request, String trace);

}
