package com.troyadevclub.integraservicios.service.impl;

import ch.qos.logback.core.util.StringUtil;
import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.dto.feature.AssociateFeatureRequestDTO;
import com.troyadevclub.integraservicios.dto.feature.CreateFeatureRequestDTO;
import com.troyadevclub.integraservicios.dto.feature.GetAssociationsProjection;
import com.troyadevclub.integraservicios.entity.FeatureEntity;
import com.troyadevclub.integraservicios.entity.projection.DBGeneralResponseProjection;
import com.troyadevclub.integraservicios.repository.FeatureAssociationRepository;
import com.troyadevclub.integraservicios.repository.FeatureRepository;
import com.troyadevclub.integraservicios.service.FeatureService;
import com.troyadevclub.integraservicios.utils.Constants;
import com.troyadevclub.integraservicios.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private FeatureAssociationRepository featureAssociationRepository;

    @Override
    public APIResponseDTO<Object> create(CreateFeatureRequestDTO request, HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<Object> response;
        try {
            String userId = Utils.getUserID(httpRequest);
            DBGeneralResponseProjection dbResponse = featureRepository.create(request.getName(), request.getDescription());
            if (Objects.isNull(dbResponse) || dbResponse.getres_msg().isBlank() || dbResponse.getres_code().isBlank()) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_REGISTER.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - DB Response: {} - {}", trace, dbResponse.getres_msg(), dbResponse.getres_code());
            if (!Objects.equals("00", dbResponse.getres_code())) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_REGISTER.getMessage()).data(dbResponse.getres_msg()).build();
                return response;
            }
            log.info("Trace <{}> - Creación de característica <{}> para unidad <{}> exitoso", trace, request.getName(), userId);
            response = APIResponseDTO.builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_REGISTER.getMessage()).data(dbResponse.getres_data()).build();
        } catch (Exception e) {
            response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - ", trace, e);
        }
        return response;
    }

    @Override
    public APIResponseDTO<List<FeatureEntity>> getAll(HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<List<FeatureEntity>> response;
        try {
            String userId = Utils.getUserID(httpRequest);
            List<FeatureEntity> entities = featureRepository.findAll();
            if (Objects.isNull(entities) || entities.isEmpty()) {
                response = APIResponseDTO.<List<FeatureEntity>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.SUCCESS_OPERATION_WITHOUT_RESULTS.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - Consulta general de características para usuario <{}> exitosa", trace, userId);
            response = APIResponseDTO.<List<FeatureEntity>>builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_OPERATION.getMessage()).data(entities).build();
        } catch (Exception e) {
            response = APIResponseDTO.<List<FeatureEntity>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - Error general - ", trace, e);
        }
        return response;
    }

    @Override
    public APIResponseDTO<Object> associateFeature(AssociateFeatureRequestDTO request, HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<Object> response;
        try {
            String userId = Utils.getUserID(httpRequest);
            DBGeneralResponseProjection dbResponse = featureRepository.associateFeature(request.getFeatureId(), request.getResourceId(), userId, request.getValue());
            if (Objects.isNull(dbResponse) || dbResponse.getres_msg().isBlank() || dbResponse.getres_code().isBlank()) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_REGISTER.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - DB Response: {} - {}", trace, dbResponse.getres_msg(), dbResponse.getres_code());
            if (!Objects.equals("00", dbResponse.getres_code())) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_REGISTER.getMessage()).data(dbResponse.getres_msg()).build();
                return response;
            }
            log.info("Trace <{}> - Asociación de característica <{}> para unidad <{}> exitoso", trace, request.getFeatureId(), userId);
            response = APIResponseDTO.builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_REGISTER.getMessage()).build();
        } catch (Exception e) {
            response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - ", trace, e);
        }
        return response;
    }

    @Override
    public APIResponseDTO<List<GetAssociationsProjection>> getAssociations(String unitUserId, String resourceId, HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<List<GetAssociationsProjection>> response;
        try {
            if (StringUtil.isNullOrEmpty(unitUserId))
                unitUserId = Utils.getUserID(httpRequest);
            List<GetAssociationsProjection> entities = featureAssociationRepository.getAssociations(unitUserId, resourceId);
            if (Objects.isNull(entities) || entities.isEmpty()) {
                response = APIResponseDTO.<List<GetAssociationsProjection>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.SUCCESS_OPERATION_WITHOUT_RESULTS.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - Consulta de asociaciones de características para usuario <{}> exitoso", trace, unitUserId);
            response = APIResponseDTO.<List<GetAssociationsProjection>>builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_OPERATION.getMessage()).data(entities).build();
            log.info(response);
        } catch (Exception e) {
            response = APIResponseDTO.<List<GetAssociationsProjection>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - Error general - ", trace, e);
        }
        return response;
    }

}
