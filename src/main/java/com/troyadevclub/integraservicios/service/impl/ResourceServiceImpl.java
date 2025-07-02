package com.troyadevclub.integraservicios.service.impl;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.dto.feature.GetByResourceTypeProjection;
import com.troyadevclub.integraservicios.dto.resourceType.CreateResourceRequestDTO;
import com.troyadevclub.integraservicios.entity.ResourceEntity;
import com.troyadevclub.integraservicios.entity.projection.DBGeneralResponseProjection;
import com.troyadevclub.integraservicios.repository.ResourceRepository;
import com.troyadevclub.integraservicios.service.ResourceService;
import com.troyadevclub.integraservicios.utils.Constants;
import com.troyadevclub.integraservicios.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public APIResponseDTO<Object> create(CreateResourceRequestDTO request, HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<Object> response;
        try {
            DBGeneralResponseProjection dbResponse = resourceRepository.create(request.getName(), request.getResourceTypeId());
            if (Objects.isNull(dbResponse) || dbResponse.getres_msg().isBlank() || dbResponse.getres_code().isBlank()) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_REGISTER.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - DB Response: {} - {}", trace, dbResponse.getres_msg(), dbResponse.getres_code());
            if (!Objects.equals("00", dbResponse.getres_code())) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_REGISTER.getMessage()).data(dbResponse.getres_msg()).build();
                return response;
            }
            log.info("Trace <{}> - Creación de recurso <{}> exitoso", trace, request.getName());
            response = APIResponseDTO.builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_REGISTER.getMessage()).data(dbResponse.getres_data()).build();
        } catch (JSONException | JWTVerificationException e1) {
            response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FORBIDDEN.getMessage()).build();
            log.error("Trace <{}> - ", trace, e1);
        } catch (Exception e2) {
            response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - ", trace, e2);
        }
        return response;
    }

    @Override
    public APIResponseDTO<List<ResourceEntity>> getAll(HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<List<ResourceEntity>> response;
        try {
            String userId = Utils.getUserID(httpRequest);
            List<ResourceEntity> entities = resourceRepository.findAll();
            if (Objects.isNull(entities) || entities.isEmpty()) {
                response = APIResponseDTO.<List<ResourceEntity>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.SUCCESS_OPERATION_WITHOUT_RESULTS.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - Consulta general de tipos de recurso para usuario <{}> exitosa", trace, userId);
            response = APIResponseDTO.<List<ResourceEntity>>builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_OPERATION.getMessage()).data(entities).build();
        } catch (Exception e) {
            response = APIResponseDTO.<List<ResourceEntity>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - Error general - ", trace, e);
        }
        return response;
    }

    @Override
    public APIResponseDTO<List<GetByResourceTypeProjection>> getByResourceType(String resourceTypeId, HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<List<GetByResourceTypeProjection>> response;
        try {
            List<GetByResourceTypeProjection> entities = resourceRepository.findAllByTIREID(Integer.valueOf(resourceTypeId));
            if (Objects.isNull(entities) || entities.isEmpty()) {
                response = APIResponseDTO.<List<GetByResourceTypeProjection>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.SUCCESS_OPERATION_WITHOUT_RESULTS.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - Consulta de recursos para tipo de recurso <{}> exitosa", trace, resourceTypeId);
            response = APIResponseDTO.<List<GetByResourceTypeProjection>>builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_OPERATION.getMessage()).data(entities).build();
        } catch (Exception e) {
            response = APIResponseDTO.<List<GetByResourceTypeProjection>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - Error general - ", trace, e);
        }
        return response;
    }

    @Override
    public APIResponseDTO<Object> associateResource(String resourceId, String quantity, HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<Object> response;
        try {
            String userId = Utils.getUserID(httpRequest);
            DBGeneralResponseProjection dbResponse = resourceRepository.associateResource(resourceId, quantity, userId);
            if (Objects.isNull(dbResponse) || dbResponse.getres_msg().isBlank() || dbResponse.getres_code().isBlank()) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_REGISTER.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - DB Response: {} - {}", trace, dbResponse.getres_msg(), dbResponse.getres_code());
            if (!Objects.equals("00", dbResponse.getres_code())) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_REGISTER.getMessage()).data(dbResponse.getres_msg()).build();
                return response;
            }
            log.info("Trace <{}> - Asociación de recurso <{}> para unidad <{}> exitoso", trace, resourceId, userId);
            response = APIResponseDTO.builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_REGISTER.getMessage()).build();
        } catch (JSONException | JWTVerificationException e1) {
            response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FORBIDDEN.getMessage()).build();
            log.error("Trace <{}> - ", trace, e1);
        } catch (Exception e2) {
            response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - ", trace, e2);
        }
        return response;
    }

}
