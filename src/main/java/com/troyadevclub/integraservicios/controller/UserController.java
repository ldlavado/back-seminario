package com.troyadevclub.integraservicios.controller;

import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.service.UserService;
import com.troyadevclub.integraservicios.utils.Constants;
import com.troyadevclub.integraservicios.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping(value = Constants.Path.USER_PATH)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(produces = {"application/json; charset=utf-8" })
    public ResponseEntity<APIResponseDTO<List<Object>>> getUsers(@RequestParam @NotBlank @Pattern(regexp = "^(client|employee|unit|all)$", message = "Tipo de usuario inválido", flags = Pattern.Flag.CASE_INSENSITIVE) String type, HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.GET_USERS;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<List<Object>> response;
        response = userService.getUsers(type, httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

    @PostMapping(path = Constants.Path.UNLOCK_PATH, produces = {"application/json; charset=utf-8" })
    public ResponseEntity<APIResponseDTO<Object>> unlockUser(@RequestParam @Range(min = 0) Integer id, HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.UNLOCK_USER;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<Object> response;
        response = userService.unlockUser(id, httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

    @PutMapping(path = Constants.Path.UPDATE_PATH, produces = {"application/json; charset=utf-8" })
    public ResponseEntity<APIResponseDTO<Object>> update(@RequestParam @Range(min = 1) Integer m, HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.UPDATE_USER;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<Object> response;
        response = userService.updateUser(m, httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

}
