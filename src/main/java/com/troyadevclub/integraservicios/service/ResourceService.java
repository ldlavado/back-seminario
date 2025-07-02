package com.troyadevclub.integraservicios.service;

import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.dto.feature.GetByResourceTypeProjection;
import com.troyadevclub.integraservicios.dto.resourceType.CreateResourceRequestDTO;
import com.troyadevclub.integraservicios.entity.ResourceEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ResourceService {

    APIResponseDTO<Object> create(CreateResourceRequestDTO request, HttpServletRequest httpRequest, String trace);

    APIResponseDTO<List<ResourceEntity>> getAll(HttpServletRequest httpRequest, String trace);

    APIResponseDTO<List<GetByResourceTypeProjection>> getByResourceType(String resourceTypeId, HttpServletRequest httpRequest, String trace);

    APIResponseDTO<Object> associateResource(String resourceId, String quantity, HttpServletRequest httpRequest, String trace);

}
