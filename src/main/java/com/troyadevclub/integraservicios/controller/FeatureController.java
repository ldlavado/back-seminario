package com.troyadevclub.integraservicios.controller;

import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.dto.feature.AssociateFeatureRequestDTO;
import com.troyadevclub.integraservicios.dto.feature.CreateFeatureRequestDTO;
import com.troyadevclub.integraservicios.dto.feature.GetAssociationsProjection;
import com.troyadevclub.integraservicios.entity.FeatureEntity;
import com.troyadevclub.integraservicios.service.FeatureService;
import com.troyadevclub.integraservicios.utils.Constants;
import com.troyadevclub.integraservicios.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping(value = Constants.Path.FEATURE_PATH)
public class FeatureController {

    @Autowired
    private FeatureService featureService;

    @PostMapping(consumes = { "application/json" }, produces = {"application/json; charset=utf-8" })
    public ResponseEntity<APIResponseDTO<Object>> createFeature(@Valid @RequestBody CreateFeatureRequestDTO request, BindingResult result, HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.CREATE_FEATURE;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<Object> response;
        if(result.hasErrors()) {
            log.error(Constants.Message.REQUEST_ERROR);
            List<String> errors = Utils.getRequestFieldErrors(result);
            response = APIResponseDTO.builder().status(Boolean.FALSE).message(String.format("%1$s - %2$s", Constants.APIMessageType.INVALID_REQUEST.getMessage(), errors)).build();
            log.error("Errores del request: {}", Utils.objectToJson(errors));
            log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
            return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
        }
        response = featureService.create(request, httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

    @GetMapping(path = Constants.Path.ALL_PATH, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<APIResponseDTO<List<FeatureEntity>>> getAll(HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.GET_ALL_FEATURES;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<List<FeatureEntity>> response = featureService.getAll(httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

    @PostMapping(path = Constants.Path.ASSOCIATION_PATH, consumes = { "application/json" }, produces = {"application/json; charset=utf-8" })
    public ResponseEntity<APIResponseDTO<Object>> associateResourceType(@Valid @RequestBody AssociateFeatureRequestDTO request, BindingResult result, HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.ASSOCIATE_FEATURE;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<Object> response;
        if(result.hasErrors()) {
            log.error(Constants.Message.REQUEST_ERROR);
            List<String> errors = Utils.getRequestFieldErrors(result);
            response = APIResponseDTO.builder().status(Boolean.FALSE).message(String.format("%1$s - %2$s", Constants.APIMessageType.INVALID_REQUEST.getMessage(), errors)).build();
            log.error("Errores del request: {}", Utils.objectToJson(errors));
            log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
            return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
        }
        response = featureService.associateFeature(request, httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

    @GetMapping(produces = {"application/json; charset=utf-8" })
    public ResponseEntity<APIResponseDTO<List<GetAssociationsProjection>>> getAssociations(@RequestParam @Range(min = 0, max = 999) Integer resourceId,
                                                                                          @RequestParam(required = false) @Range(min = 0, max = 999) Integer unitId,
                                                                                          HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.GET_FEATURE_ASSOCIATIONS;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<List<GetAssociationsProjection>> response;
        response = featureService.getAssociations(Objects.toString(unitId, null), resourceId.toString(), httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

}
