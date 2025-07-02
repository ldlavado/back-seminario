package com.troyadevclub.integraservicios.controller;

import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.dto.feature.GetByResourceTypeProjection;
import com.troyadevclub.integraservicios.dto.resourceType.CreateResourceRequestDTO;
import com.troyadevclub.integraservicios.entity.ResourceEntity;
import com.troyadevclub.integraservicios.service.ResourceService;
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
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping(value = Constants.Path.RESOURCE_PATH)
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping(consumes = { "application/json" }, produces = {"application/json; charset=utf-8" })
    public ResponseEntity<APIResponseDTO<Object>> createResource(@Valid @RequestBody CreateResourceRequestDTO request, BindingResult result, HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.CREATE_RESOURCE;
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
        response = resourceService.create(request, httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

    @GetMapping(path = Constants.Path.ALL_PATH, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<APIResponseDTO<List<ResourceEntity>>> getAll(HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.GET_ALL_RESOURCES;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<List<ResourceEntity>> response = resourceService.getAll(httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

    @GetMapping(produces = {"application/json; charset=utf-8"})
    public ResponseEntity<APIResponseDTO<List<GetByResourceTypeProjection>>> getByResourceType(@RequestParam @Range(min = 0, max = 999) String type, HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.GET_RESOURCE_BY_TYPE;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<List<GetByResourceTypeProjection>> response = resourceService.getByResourceType(type, httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

    @PostMapping(path = Constants.Path.ASSOCIATION_PATH, produces = {"application/json; charset=utf-8" })
    public ResponseEntity<APIResponseDTO<Object>> associateResource(@RequestParam @Range(min = 0, max = 999) Integer resourceId, @RequestParam @Range(min = 0, max = 999) Integer quantity, HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.ASSOCIATE_RESOURCE;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<Object> response = resourceService.associateResource(resourceId.toString(), quantity.toString(), httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

}
