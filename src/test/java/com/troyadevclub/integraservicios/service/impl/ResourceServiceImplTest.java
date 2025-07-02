package com.troyadevclub.integraservicios.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.dto.feature.GetByResourceTypeProjection;
import com.troyadevclub.integraservicios.dto.resourceType.CreateResourceRequestDTO;
import com.troyadevclub.integraservicios.entity.ResourceEntity;
import com.troyadevclub.integraservicios.entity.projection.DBGeneralResponseProjection;
import com.troyadevclub.integraservicios.repository.ResourceRepository;
import com.troyadevclub.integraservicios.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ResourceServiceImplTest {

    @Mock
    private ResourceRepository resourceRepository;

    @InjectMocks
    private ResourceServiceImpl resourceService;

    @Autowired
    private Environment env;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateResourceSuccess() {
        CreateResourceRequestDTO request = new CreateResourceRequestDTO();
        request.setName("Test Resource");
        request.setResourceTypeId("1");

        DBGeneralResponseProjection dbResponse = mock(DBGeneralResponseProjection.class);
        when(dbResponse.getres_code()).thenReturn("00");
        when(dbResponse.getres_msg()).thenReturn("Success");

        when(resourceRepository.create(anyString(), anyString())).thenReturn(dbResponse);

        APIResponseDTO<Object> response = resourceService.create(request, mock(HttpServletRequest.class), "trace");

        assertTrue(response.getStatus());
        assertEquals(Constants.APIMessageType.SUCCESS_REGISTER.getMessage(), response.getMessage());
    }

    @Test
    void testGetAllResourcesSuccess() {
        List<ResourceEntity> entities = Collections.singletonList(new ResourceEntity());
        when(resourceRepository.findAll()).thenReturn(entities);

        HttpServletRequest request = mock(HttpServletRequest.class);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 3600);
        JSONObject userData = new JSONObject();
        userData.put("USUA_ID", "1");
        userData.put("USUA_CORREO", "test@email.com");
        userData.put("TIPO", "test");
        String testToken = JWT.create()
                .withClaim("userData", userData.toString())
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC512(Base64.getDecoder().decode(Objects.requireNonNull(env.getProperty("api_auth_secret")))));
        when(request.getHeader("Authorization")).thenReturn(Constants.Util.BEARER + testToken);

        APIResponseDTO<List<ResourceEntity>> response = resourceService.getAll(request, "trace");

        assertTrue(response.getStatus());
        assertEquals(Constants.APIMessageType.SUCCESS_OPERATION.getMessage(), response.getMessage());
        assertEquals(entities, response.getData());
    }

    @Test
    void testGetByResourceTypeSuccess() {
        GetByResourceTypeProjection projection = mock(GetByResourceTypeProjection.class);
        List<GetByResourceTypeProjection> projections = Collections.singletonList(projection);
        when(resourceRepository.findAllByTIREID(anyInt())).thenReturn(projections);

        APIResponseDTO<List<GetByResourceTypeProjection>> response = resourceService.getByResourceType("1", mock(HttpServletRequest.class), "trace");

        assertTrue(response.getStatus());
        assertEquals(Constants.APIMessageType.SUCCESS_OPERATION.getMessage(), response.getMessage());
        assertEquals(projections, response.getData());
    }

    @Test
    void testAssociateResourceSuccess() {
        DBGeneralResponseProjection dbResponse = mock(DBGeneralResponseProjection.class);
        when(dbResponse.getres_code()).thenReturn("00");
        when(dbResponse.getres_msg()).thenReturn("Success");
        when(resourceRepository.associateResource(anyString(), anyString(), anyString())).thenReturn(dbResponse);

        HttpServletRequest request = mock(HttpServletRequest.class);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 3600);
        JSONObject userData = new JSONObject();
        userData.put("USUA_ID", "1");
        userData.put("USUA_CORREO", "test@email.com");
        userData.put("TIPO", "test");
        String testToken = JWT.create()
                .withClaim("userData", userData.toString())
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC512(Base64.getDecoder().decode(Objects.requireNonNull(env.getProperty("api_auth_secret")))));
        when(request.getHeader("Authorization")).thenReturn(Constants.Util.BEARER + testToken);

        APIResponseDTO<Object> response = resourceService.associateResource("1", "10", request, "trace");

        assertTrue(response.getStatus());
        assertEquals(Constants.APIMessageType.SUCCESS_REGISTER.getMessage(), response.getMessage());
    }
}
