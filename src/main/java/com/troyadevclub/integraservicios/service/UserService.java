package com.troyadevclub.integraservicios.service;

import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService {

    APIResponseDTO<List<Object>> getUsers(String type, HttpServletRequest httpRequest, String trace);

    APIResponseDTO<Object> unlockUser(Integer id, HttpServletRequest httpRequest, String trace);

    APIResponseDTO<Object> updateUser(Integer m, HttpServletRequest httpRequest, String trace);

}
