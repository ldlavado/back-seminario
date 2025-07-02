package com.troyadevclub.integraservicios.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import com.troyadevclub.integraservicios.utils.Constants.APIMessageType;

@Component
@Log4j2
public class Utils implements ApplicationContextAware {

    private static Environment env;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        env = applicationContext.getEnvironment();
    }

    public static List<String> getRequestFieldErrors(BindingResult result) {
        List<String> errors = result.getFieldErrors()
                .stream()
                .map(err -> "El campo " + err.getField() + " - " + err.getDefaultMessage())
                .collect(Collectors.toList());
        log.info("Errores -> {}", objectToJson(errors));
        return errors;
    }

    public static String objectToJson(Object object) {
        String response = null;
        try {
            response = new Gson().toJson(object);
        } catch (Exception e) {
            log.error("Error creating JSON", e);
        }
        return response;
    }

    public static HttpStatus findCodeByMessage(APIResponseDTO<?> response) {
        Optional<APIMessageType> ApiMsg = Arrays.stream(APIMessageType.values())
                                                            .filter(apiMessageType -> Objects.equals(apiMessageType.getMessage(), response.getMessage()))
                                                            .findFirst();
        return ApiMsg.isPresent()?ApiMsg.get().getCode():HttpStatus.OK;
    }

    public static DecodedJWT getDecodedJWT(HttpServletRequest request) throws JWTVerificationException  {
        Algorithm algorithm = Algorithm.HMAC512(Base64.getDecoder().decode(Objects.requireNonNull(env.getProperty("api_auth_secret"))));
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(request.getHeader(Constants.Util.HEADER).replace(Constants.Util.BEARER, ""));
    }

    public static String getUserID(HttpServletRequest httpRequest) throws JWTVerificationException {
        DecodedJWT jwt = Utils.getDecodedJWT(httpRequest);
        String userData = jwt.getClaim("userData").asString();
        JSONObject jsonObject = new JSONObject(userData);
        return String.valueOf(jsonObject.get("USUA_ID"));
    }

}
