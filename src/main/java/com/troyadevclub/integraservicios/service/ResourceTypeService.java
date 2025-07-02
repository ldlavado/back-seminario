package com.troyadevclub.integraservicios.service;

import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.dto.resourceType.CreateResourceTypeRequestDTO;
import com.troyadevclub.integraservicios.entity.ResourceTypeEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ResourceTypeService {

    APIResponseDTO<Object> create(CreateResourceTypeRequestDTO request, HttpServletRequest httpRequest, String trace);

    APIResponseDTO<Object> associateResourceType(String resourceTypeId, HttpServletRequest httpRequest, String trace);

    APIResponseDTO<List<ResourceTypeEntity>> getByUnit(String unitId, HttpServletRequest httpRequest, String trace);

    APIResponseDTO<List<ResourceTypeEntity>> getAll(HttpServletRequest httpRequest, String trace);

}
