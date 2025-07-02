package com.troyadevclub.integraservicios.service.impl;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.dto.resourceType.CreateResourceTypeRequestDTO;
import com.troyadevclub.integraservicios.entity.ResourceTypeEntity;
import com.troyadevclub.integraservicios.entity.projection.DBGeneralResponseProjection;
import com.troyadevclub.integraservicios.repository.ResourceTypeRepository;
import com.troyadevclub.integraservicios.service.ResourceTypeService;
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
public class ResourceTypeServiceImpl implements ResourceTypeService {

    @Autowired
    private ResourceTypeRepository resourceTypeRepository;

    @Override
    public APIResponseDTO<Object> create(CreateResourceTypeRequestDTO request, HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<Object> response;
        try {
            String userId = Utils.getUserID(httpRequest);
            DBGeneralResponseProjection dbResponse = resourceTypeRepository.create(request.getName(), request.getDescription());
            if (Objects.isNull(dbResponse) || dbResponse.getres_msg().isBlank() || dbResponse.getres_code().isBlank()) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_REGISTER.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - DB Response: {} - {}", trace, dbResponse.getres_msg(), dbResponse.getres_code());
            if (!Objects.equals("00", dbResponse.getres_code())) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_REGISTER.getMessage()).data(dbResponse.getres_msg()).build();
                return response;
            }
            log.info("Trace <{}> - Creación de tipo de recurso <{}> para unidad <{}> exitoso", trace, request.getName(), userId);
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
    public APIResponseDTO<Object> associateResourceType(String resourceTypeId, HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<Object> response;
        try {
            String userId = Utils.getUserID(httpRequest);
            DBGeneralResponseProjection dbResponse = resourceTypeRepository.associateResourceType(resourceTypeId, userId);
            if (Objects.isNull(dbResponse) || dbResponse.getres_msg().isBlank() || dbResponse.getres_code().isBlank()) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_REGISTER.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - DB Response: {} - {}", trace, dbResponse.getres_msg(), dbResponse.getres_code());
            if (!Objects.equals("00", dbResponse.getres_code())) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_REGISTER.getMessage()).data(dbResponse.getres_msg()).build();
                return response;
            }
            log.info("Trace <{}> - Asociación de tipo de recurso <{}> para unidad <{}> exitoso", trace, resourceTypeId, userId);
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

    @Override
    public APIResponseDTO<List<ResourceTypeEntity>> getByUnit(String unitId, HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<List<ResourceTypeEntity>> response;
        try {
            String userId = Objects.nonNull(unitId) ? unitId : Utils.getUserID(httpRequest);
            List<ResourceTypeEntity> entities = resourceTypeRepository.getByUnit(userId);
            if (Objects.isNull(entities) || entities.isEmpty()) {
                response = APIResponseDTO.<List<ResourceTypeEntity>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.SUCCESS_OPERATION_WITHOUT_RESULTS.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - Consulta de tipos de recurso para usuario <{}> exitoso", trace, userId);
            response = APIResponseDTO.<List<ResourceTypeEntity>>builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_OPERATION.getMessage()).data(entities).build();
        } catch (Exception e) {
            response = APIResponseDTO.<List<ResourceTypeEntity>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - Error general - ", trace, e);
        }
        return response;
    }

    @Override
    public APIResponseDTO<List<ResourceTypeEntity>> getAll(HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<List<ResourceTypeEntity>> response;
        try {
            String userId = Utils.getUserID(httpRequest);
            List<ResourceTypeEntity> entities = resourceTypeRepository.findAll();
            if (Objects.isNull(entities) || entities.isEmpty()) {
                response = APIResponseDTO.<List<ResourceTypeEntity>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.SUCCESS_OPERATION_WITHOUT_RESULTS.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - Consulta general de tipos de recurso para usuario <{}> exitosa", trace, userId);
            response = APIResponseDTO.<List<ResourceTypeEntity>>builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_OPERATION.getMessage()).data(entities).build();
        } catch (Exception e) {
            response = APIResponseDTO.<List<ResourceTypeEntity>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - Error general - ", trace, e);
        }
        return response;
    }

}
