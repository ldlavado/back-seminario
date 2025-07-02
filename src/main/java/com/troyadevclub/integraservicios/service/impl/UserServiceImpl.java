package com.troyadevclub.integraservicios.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.entity.projection.DBGeneralResponseProjection;
import com.troyadevclub.integraservicios.repository.UserRepository;
import com.troyadevclub.integraservicios.service.UserService;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public APIResponseDTO<List<Object>> getUsers(String type, HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<List<Object>> response;
        String usersString;
        List<Object> users;
        try {
            usersString = userRepository.getUsers(type);
            ObjectMapper mapper = new ObjectMapper();
            users = mapper.readValue(usersString, new TypeReference<>() {
            });
            if (users.isEmpty()) {
                log.info("Trace <{}> - Consulta de usuarios <{}> sin resultados", trace, type);
                response = APIResponseDTO.<List<Object>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.SUCCESS_OPERATION_WITHOUT_RESULTS.getMessage()).build();
            } else {
                log.info("Trace <{}> - Consulta de usuarios <{}> exitosa", trace, type);
                response = APIResponseDTO.<List<Object>>builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_OPERATION.getMessage()).data(users).build();
            }
            return response;
        } catch (Exception e) {
            response = APIResponseDTO.<List<Object>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - ", trace, e);
        }
        return response;
    }

    @Override
    public APIResponseDTO<Object> unlockUser(Integer id, HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<Object> response;
        try {
            DBGeneralResponseProjection dbResponse = userRepository.unlockUser(id);
            if (Objects.isNull(dbResponse) || dbResponse.getres_msg().isBlank() || dbResponse.getres_code().isBlank()) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_OPERATION.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - DB Response: {} - {}", trace, dbResponse.getres_msg(), dbResponse.getres_code());
            if (!Objects.equals("00", dbResponse.getres_code())) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_OPERATION.getMessage()).data(dbResponse.getres_msg()).build();
                return response;
            }
            log.info("Trace <{}> - Desbloqueo de usuario <{}> exitoso", trace, id);
            response = APIResponseDTO.builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_OPERATION.getMessage()).build();
            return response;
        } catch (Exception e) {
            response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - ", trace, e);
        }
        return response;
    }

    @Override
    public APIResponseDTO<Object> updateUser(Integer m, HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<Object> response;
        try {
            String userId = Utils.getUserID(httpRequest);
            Integer dbResponse = userRepository.updateUser(m, Integer.valueOf(userId));
            if (Objects.isNull(dbResponse) || dbResponse == 0) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_OPERATION.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - DB Response: {}", trace, dbResponse);
            if (dbResponse != 1) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_OPERATION.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - Actualización de usuario <{}> exitoso", trace, userId);
            response = APIResponseDTO.builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_OPERATION.getMessage()).build();
            return response;
        } catch (Exception e) {
            response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - ", trace, e);
        }
        return response;
    }

}
