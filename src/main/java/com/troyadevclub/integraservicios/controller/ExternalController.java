package com.troyadevclub.integraservicios.controller;

import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.service.ExternalService;
import com.troyadevclub.integraservicios.utils.Constants;
import com.troyadevclub.integraservicios.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Log4j2
@RestController
@RequestMapping(value = Constants.Path.EXTERNAL_PATH)
public class ExternalController {

    @Autowired
    private ExternalService externalService;

    @GetMapping(produces = {"application/json; charset=utf-8"})
    public ResponseEntity<APIResponseDTO<Object>> getExternalData(@RequestParam @Range(min = 0, max = 999) Integer id, HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.EXTERNAL_GET_DATA;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<Object> response = externalService.getExternalData(id, httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

}
