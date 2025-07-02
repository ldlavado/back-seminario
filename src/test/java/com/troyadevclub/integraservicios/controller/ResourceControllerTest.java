package com.troyadevclub.integraservicios.controller;

import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.dto.feature.GetByResourceTypeProjection;
import com.troyadevclub.integraservicios.dto.resourceType.CreateResourceRequestDTO;
import com.troyadevclub.integraservicios.entity.ResourceEntity;
import com.troyadevclub.integraservicios.service.ResourceService;
import com.troyadevclub.integraservicios.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResourceControllerTest {

    @Mock
    private ResourceService resourceService;

    @InjectMocks
    private ResourceController resourceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateResource() {
        CreateResourceRequestDTO request = new CreateResourceRequestDTO();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        APIResponseDTO<Object> serviceResponse = new APIResponseDTO<>();
        serviceResponse.setStatus(true);
        serviceResponse.setMessage(Constants.APIMessageType.SUCCESS_REGISTER.getMessage());

        when(resourceService.create(any(CreateResourceRequestDTO.class), any(HttpServletRequest.class), anyString())).thenReturn(serviceResponse);

        ResponseEntity<APIResponseDTO<Object>> response = resourceController.createResource(request, result, mock(HttpServletRequest.class));

        assertTrue(response.getBody().getStatus());
        assertEquals(Constants.APIMessageType.SUCCESS_REGISTER.getMessage(), response.getBody().getMessage());
    }

    @Test
    void testGetAllResources() {
        List<ResourceEntity> entities = Collections.singletonList(new ResourceEntity());
        APIResponseDTO<List<ResourceEntity>> serviceResponse = new APIResponseDTO<>();
        serviceResponse.setStatus(true);
        serviceResponse.setMessage(Constants.APIMessageType.SUCCESS_OPERATION.getMessage());
        serviceResponse.setData(entities);

        when(resourceService.getAll(any(HttpServletRequest.class), anyString())).thenReturn(serviceResponse);

        ResponseEntity<APIResponseDTO<List<ResourceEntity>>> response = resourceController.getAll(mock(HttpServletRequest.class));

        assertTrue(response.getBody().getStatus());
        assertEquals(Constants.APIMessageType.SUCCESS_OPERATION.getMessage(), response.getBody().getMessage());
        assertEquals(entities, response.getBody().getData());
    }

    @Test
    void testGetByResourceType() {
        List<GetByResourceTypeProjection> projections = Collections.singletonList(mock(GetByResourceTypeProjection.class));
        APIResponseDTO<List<GetByResourceTypeProjection>> serviceResponse = new APIResponseDTO<>();
        serviceResponse.setStatus(true);
        serviceResponse.setMessage(Constants.APIMessageType.SUCCESS_OPERATION.getMessage());
        serviceResponse.setData(projections);

        when(resourceService.getByResourceType(anyString(), any(HttpServletRequest.class), anyString())).thenReturn(serviceResponse);

        ResponseEntity<APIResponseDTO<List<GetByResourceTypeProjection>>> response = resourceController.getByResourceType("1", mock(HttpServletRequest.class));

        assertTrue(response.getBody().getStatus());
        assertEquals(Constants.APIMessageType.SUCCESS_OPERATION.getMessage(), response.getBody().getMessage());
        assertEquals(projections, response.getBody().getData());
    }

    @Test
    void testAssociateResource() {
        APIResponseDTO<Object> serviceResponse = new APIResponseDTO<>();
        serviceResponse.setStatus(true);
        serviceResponse.setMessage(Constants.APIMessageType.SUCCESS_REGISTER.getMessage());

        when(resourceService.associateResource(anyString(), anyString(), any(HttpServletRequest.class), anyString())).thenReturn(serviceResponse);

        ResponseEntity<APIResponseDTO<Object>> response = resourceController.associateResource(1, 10, mock(HttpServletRequest.class));

        assertTrue(response.getBody().getStatus());
        assertEquals(Constants.APIMessageType.SUCCESS_REGISTER.getMessage(), response.getBody().getMessage());
    }
}