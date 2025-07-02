package com.troyadevclub.integraservicios.controller;

import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.dto.resourceType.CreateResourceTypeRequestDTO;
import com.troyadevclub.integraservicios.entity.ResourceTypeEntity;
import com.troyadevclub.integraservicios.service.ResourceTypeService;
import com.troyadevclub.integraservicios.utils.Constants;
import com.troyadevclub.integraservicios.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Validated
@Log4j2
@RestController
@RequestMapping(value = Constants.Path.RESOURCE_PATH + "/" + Constants.Path.RESOURCE_TYPE_PATH)
public class ResourceTypeController {

    @Autowired
    private ResourceTypeService resourceTypeService;

    @PostMapping(consumes = { "application/json" }, produces = {"application/json; charset=utf-8" })
    public ResponseEntity<APIResponseDTO<Object>> createResourceType(@Valid @RequestBody CreateResourceTypeRequestDTO request, BindingResult result, HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.CREATE_RESOURCE_TYPE;
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
        response = resourceTypeService.create(request, httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

    @PostMapping(path = Constants.Path.ASSOCIATION_PATH, produces = {"application/json; charset=utf-8" })
    public ResponseEntity<APIResponseDTO<Object>> associateResourceType(@RequestParam @Range(min = 0, max = 999) Integer resourceTypeId, HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.ASSOCIATE_RESOURCE_TYPE;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<Object> response;
        response = resourceTypeService.associateResourceType(resourceTypeId.toString(), httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

    @GetMapping(produces = {"application/json; charset=utf-8"})
    public ResponseEntity<APIResponseDTO<List<ResourceTypeEntity>>> getByUnit(@RequestParam(required = false) @Range(min = 0, max = 999) String id, HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.GET_RESOURCE_TYPE_BY_UNIT;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<List<ResourceTypeEntity>> response = resourceTypeService.getByUnit(id, httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

    @GetMapping(path = Constants.Path.ALL_PATH, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<APIResponseDTO<List<ResourceTypeEntity>>> getAll(HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.GET_ALL_RESOURCE_TYPES;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<List<ResourceTypeEntity>> response = resourceTypeService.getAll(httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

}
