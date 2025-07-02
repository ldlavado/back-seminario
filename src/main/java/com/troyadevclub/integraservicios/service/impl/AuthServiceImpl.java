package com.troyadevclub.integraservicios.service.impl;

import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.entity.projection.DBGeneralResponseProjection;
import com.troyadevclub.integraservicios.dto.auth.LoginRequestDTO;
import com.troyadevclub.integraservicios.dto.auth.LoginResponseDTO;
import com.troyadevclub.integraservicios.dto.auth.RegisterRequestDTO;
import com.troyadevclub.integraservicios.repository.UserRepository;
import com.troyadevclub.integraservicios.service.AuthService;
import com.troyadevclub.integraservicios.utils.Constants;
import com.troyadevclub.integraservicios.utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Service
@Log4j2
public class AuthServiceImpl implements AuthService {

    @Autowired
    private Environment env;

    @Autowired
    private UserRepository userRepository;

    @Override
    public APIResponseDTO<Object> registerUser(RegisterRequestDTO request, String trace) {
        APIResponseDTO<Object> response;
        try {
            DBGeneralResponseProjection dbResponse = userRepository.registerUser(request.getName(), request.getLastName(), request.getPhoneNumber(), request.getEmail(), request.getAddress(), request.getPassword(), request.getUserType(), request.getUnitType(), request.getIntegrationType());
            if (Objects.isNull(dbResponse) || dbResponse.getres_msg().isBlank() || dbResponse.getres_code().isBlank()) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_REGISTER.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - DB Response: {} - {}", trace, dbResponse.getres_msg(), dbResponse.getres_code());
            if (!Objects.equals("00", dbResponse.getres_code())) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_REGISTER.getMessage()).data(dbResponse.getres_msg()).build();
                return response;
            }
            log.info("Trace <{}> - Registro de usuario con email <{}> exitoso", trace, request.getEmail());
            response = APIResponseDTO.builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_REGISTER.getMessage()).build();
        } catch (Exception e) {
            response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - ", trace, e);
        }
        return response;
    }

    @Override
    public APIResponseDTO<LoginResponseDTO> loginUser(LoginRequestDTO request, String trace) {
        APIResponseDTO<LoginResponseDTO> response;
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        try {
            DBGeneralResponseProjection dbResponse = userRepository.loginUser(request.getEmail(), request.getPassword());
            if (Objects.isNull(dbResponse) || dbResponse.getres_msg().isBlank() || dbResponse.getres_code().isBlank()) {
                response = APIResponseDTO.<LoginResponseDTO>builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_OPERATION.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - DB Response: {} - {} - {}", trace, dbResponse.getres_msg(), dbResponse.getres_code(), dbResponse.getres_data());
            if (!Objects.equals("00", dbResponse.getres_code())) {
                loginResponseDTO.setError(dbResponse.getres_msg());
                response = APIResponseDTO.<LoginResponseDTO>builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_OPERATION.getMessage()).data(loginResponseDTO).build();
                return response;
            }

            String userData = Utils.objectToJson(dbResponse.getres_data());
            String formattedData = userData.substring(1, userData.length() - 1).replaceAll("\\\\","");
            JSONObject jsonObject = new JSONObject(formattedData);

            Algorithm algorithmHS = Algorithm.HMAC512(Base64.getDecoder().decode(Objects.requireNonNull(env.getProperty("api_auth_secret"))));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(Objects.requireNonNull(env.getProperty("api_auth_secret_duration")))/60);
            String token = JWT.create()
                    .withIssuer("TroyaDevClub SAS.")
                    .withIssuedAt(new Date())
                    .withExpiresAt(calendar.getTime())
                    .withSubject(jsonObject.get("USUA_ID").toString())
                    .withClaim("userData", jsonObject.toString())
                    .sign(algorithmHS);
            loginResponseDTO.setToken(token);
            response = APIResponseDTO.<LoginResponseDTO>builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_OPERATION.getMessage()).data(loginResponseDTO).build();
        } catch (Exception e) {
            response = APIResponseDTO.<LoginResponseDTO>builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - ", trace, e);
        }
        return response;
    }

}
