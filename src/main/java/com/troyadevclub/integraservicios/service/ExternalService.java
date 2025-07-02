package com.troyadevclub.integraservicios.service;

import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface ExternalService {

    APIResponseDTO<Object> getExternalData(Integer id, HttpServletRequest httpRequest, String trace);

}
