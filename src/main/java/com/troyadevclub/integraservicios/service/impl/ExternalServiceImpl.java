package com.troyadevclub.integraservicios.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.dto.feature.ExternalDataResponseDTO;
import com.troyadevclub.integraservicios.repository.FeatureRepository;
import com.troyadevclub.integraservicios.service.ExternalService;
import com.troyadevclub.integraservicios.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Log4j2
public class ExternalServiceImpl implements ExternalService {

    @Autowired
    private FeatureRepository featureRepository;

    @Override
    public APIResponseDTO<Object> getExternalData(Integer id, HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<Object> response;
        try {
            String dbRes = featureRepository.getExternalData(id);
            ObjectMapper mapper = new ObjectMapper();
            ExternalDataResponseDTO externalDataResponseDTO = mapper.readValue(dbRes, new TypeReference<>() {});
            if (Objects.isNull(dbRes) || dbRes.isEmpty()) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.SUCCESS_OPERATION_WITHOUT_RESULTS.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - Consulta externa de datos exitosa", trace);
            response = APIResponseDTO.builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_OPERATION.getMessage()).data(externalDataResponseDTO).build();
        } catch (Exception e) {
            response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - Error general - ", trace, e);
        }
        return response;
    }

}
