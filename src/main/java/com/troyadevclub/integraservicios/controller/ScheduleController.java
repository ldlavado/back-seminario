package com.troyadevclub.integraservicios.controller;

import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.dto.schedule.EditResourceTypeScheduleRequestDTO;
import com.troyadevclub.integraservicios.dto.schedule.EditUnitScheduleRequestDTO;
import com.troyadevclub.integraservicios.entity.ResourceTypeScheduleEntity;
import com.troyadevclub.integraservicios.entity.UnitScheduleEntity;
import com.troyadevclub.integraservicios.service.ScheduleService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping(value = Constants.Path.SCHEDULE_PATH)
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping(path = Constants.Path.UNIT_SCHEDULE_PATH, produces = {"application/json; charset=utf-8" })
    public ResponseEntity<APIResponseDTO<List<UnitScheduleEntity>>> getUnitSchedule(HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.GET_UNIT_SCHEDULE;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<List<UnitScheduleEntity>> response;
        response = scheduleService.getUnitSchedule(httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

    @PutMapping(path = Constants.Path.UNIT_SCHEDULE_PATH, consumes = { "application/json" }, produces = {"application/json; charset=utf-8" })
    public ResponseEntity<APIResponseDTO<Map<String, String>>> editUnitSchedule(@Valid @RequestBody EditUnitScheduleRequestDTO request, BindingResult result, HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.EDIT_UNIT_SCHEDULE;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<Map<String, String>> response;
        if(result.hasErrors()) {
            log.error(Constants.Message.REQUEST_ERROR);
            List<String> errors = Utils.getRequestFieldErrors(result);
            response = APIResponseDTO.<Map<String, String>>builder().status(Boolean.FALSE).message(String.format("%1$s - %2$s", Constants.APIMessageType.INVALID_REQUEST.getMessage(), errors)).build();
            log.error("Errores del request: {}", Utils.objectToJson(errors));
            log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
            return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
        }
        response = scheduleService.editUnitSchedule(request, httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

    @GetMapping(path = Constants.Path.RESOURCE_TYPE_SCHEDULE_PATH, produces = {"application/json; charset=utf-8" })
    public ResponseEntity<APIResponseDTO<List<ResourceTypeScheduleEntity>>> getResourceTypeSchedule(@RequestParam @Range(min = 0, max = 999) Integer id,
                                                                                                    @RequestParam(required = false) @Range(min = 0, max = 999) Integer unitId,
                                                                                                    HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.GET_RESOURCE_TYPE_SCHEDULE;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<List<ResourceTypeScheduleEntity>> response;
        response = scheduleService.getResourceTypeSchedule(Objects.toString(unitId, null), id.toString(), httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

    @PutMapping(path = Constants.Path.RESOURCE_TYPE_SCHEDULE_PATH, consumes = { "application/json" }, produces = {"application/json; charset=utf-8" })
    public ResponseEntity<APIResponseDTO<Map<String, String>>> editResourceTypeSchedule(@Valid @RequestBody EditResourceTypeScheduleRequestDTO request, BindingResult result, HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.EDIT_RESOURCE_TYPE_SCHEDULE;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<Map<String, String>> response;
        if(result.hasErrors()) {
            log.error(Constants.Message.REQUEST_ERROR);
            List<String> errors = Utils.getRequestFieldErrors(result);
            response = APIResponseDTO.<Map<String, String>>builder().status(Boolean.FALSE).message(String.format("%1$s - %2$s", Constants.APIMessageType.INVALID_REQUEST.getMessage(), errors)).build();
            log.error("Errores del request: {}", Utils.objectToJson(errors));
            log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
            return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
        }
        response = scheduleService.editResourceTypeSchedule(request, httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

}
