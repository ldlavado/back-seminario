package com.troyadevclub.integraservicios.service.impl;

import ch.qos.logback.core.util.StringUtil;
import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.dto.schedule.EditResourceTypeScheduleRequestDTO;
import com.troyadevclub.integraservicios.dto.schedule.EditUnitScheduleRequestDTO;
import com.troyadevclub.integraservicios.entity.ResourceTypeScheduleEntity;
import com.troyadevclub.integraservicios.entity.UnitScheduleEntity;
import com.troyadevclub.integraservicios.entity.projection.DBGeneralResponseProjection;
import com.troyadevclub.integraservicios.repository.ResourceTypeScheduleRepository;
import com.troyadevclub.integraservicios.repository.UnitScheduleRepository;
import com.troyadevclub.integraservicios.service.ScheduleService;
import com.troyadevclub.integraservicios.utils.Constants;
import com.troyadevclub.integraservicios.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Log4j2
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private UnitScheduleRepository unitScheduleRepository;

    @Autowired
    private ResourceTypeScheduleRepository resourceTypeScheduleRepository;

    @Override
    public APIResponseDTO<List<UnitScheduleEntity>> getUnitSchedule(HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<List<UnitScheduleEntity>> response;
        List<UnitScheduleEntity> schedules;
        try {
            String userId = Utils.getUserID(httpRequest);
            schedules = unitScheduleRepository.getUnitSchedules(userId);
            if (schedules.isEmpty()) {
                log.info("Trace <{}> - Consulta de horarios para unidad <{}> sin resultados", trace, userId);
                response = APIResponseDTO.<List<UnitScheduleEntity>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.SUCCESS_OPERATION_WITHOUT_RESULTS.getMessage()).build();
            } else {
                log.info("Trace <{}> - Consulta de horarios para unidad <{}> exitosa", trace, userId);
                response = APIResponseDTO.<List<UnitScheduleEntity>>builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_OPERATION.getMessage()).data(schedules).build();
            }
            return response;
        } catch (Exception e) {
            response = APIResponseDTO.<List<UnitScheduleEntity>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - ", trace, e);
        }
        return response;
    }

    @Override
    public APIResponseDTO<Map<String, String>> editUnitSchedule(EditUnitScheduleRequestDTO request, HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<Map<String, String>> response;
        Map<String, String> errors = new HashMap<>();
        try {
            String userId = Utils.getUserID(httpRequest);
            for (EditUnitScheduleRequestDTO.Schedule s: request.getSchedules()) {
                DBGeneralResponseProjection dbResponse = unitScheduleRepository.updateUnitSchedule(s.getStartTime(), s.getEndTime(), s.getDay(), userId);
                if (Objects.isNull(dbResponse) || dbResponse.getres_msg().isBlank() || dbResponse.getres_code().isBlank()) {
                    errors.put(s.getDay(), Constants.APIMessageType.GENERAL_ERROR.getMessage());
                }
                log.info("Trace <{}> - DB Response: {} - {}", trace, dbResponse.getres_msg(), dbResponse.getres_code());
                if (!Objects.equals("00", dbResponse.getres_code())) {
                    errors.put(s.getDay(), dbResponse.getres_msg());
                }
            }
            if (errors.isEmpty()) {
                log.info("Trace <{}> - Actualización de horarios para unidad <{}> exitosa", trace, userId);
                response = APIResponseDTO.<Map<String, String>>builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_UPDATE.getMessage()).build();
            } else {
                log.info("Trace <{}> - Actualización de horarios para unidad <{}> realizada con errores", trace, userId);
                response = APIResponseDTO.<Map<String, String>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.SUCCESS_UPDATE_WITH_ERRORS.getMessage()).data(errors).build();
            }
            return response;
        } catch (Exception e) {
            response = APIResponseDTO.<Map<String, String>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - ", trace, e);
        }
        return response;
    }

    @Override
    public APIResponseDTO<List<ResourceTypeScheduleEntity>> getResourceTypeSchedule(String unitUserId, String resourceTypeId, HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<List<ResourceTypeScheduleEntity>> response;
        List<ResourceTypeScheduleEntity> schedules;
        try {
            if (StringUtil.isNullOrEmpty(unitUserId))
                unitUserId = Utils.getUserID(httpRequest);
            schedules = resourceTypeScheduleRepository.getResourceTypeSchedules(unitUserId, resourceTypeId);
            if (schedules.isEmpty()) {
                log.info("Trace <{}> - Consulta de horarios para unidad <{}> sin resultados", trace, unitUserId);
                response = APIResponseDTO.<List<ResourceTypeScheduleEntity>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.SUCCESS_OPERATION_WITHOUT_RESULTS.getMessage()).build();
            } else {
                log.info("Trace <{}> - Consulta de horarios para unidad <{}> exitosa", trace, unitUserId);
                response = APIResponseDTO.<List<ResourceTypeScheduleEntity>>builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_OPERATION.getMessage()).data(schedules).build();
            }
            return response;
        } catch (Exception e) {
            response = APIResponseDTO.<List<ResourceTypeScheduleEntity>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - ", trace, e);
        }
        return response;
    }

    @Override
    public APIResponseDTO<Map<String, String>> editResourceTypeSchedule(EditResourceTypeScheduleRequestDTO request, HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<Map<String, String>> response;
        Map<String, String> errors = new HashMap<>();
        try {
            String userId = Utils.getUserID(httpRequest);
            for (EditUnitScheduleRequestDTO.Schedule s: request.getSchedules()) {
                DBGeneralResponseProjection dbResponse = resourceTypeScheduleRepository.updateResourceTypeSchedule(s.getStartTime(), s.getEndTime(), s.getDay(), request.getResourceTypeId(), userId);
                if (Objects.isNull(dbResponse) || dbResponse.getres_msg().isBlank() || dbResponse.getres_code().isBlank()) {
                    errors.put(s.getDay(), Constants.APIMessageType.GENERAL_ERROR.getMessage());
                }
                log.info("Trace <{}> - DB Response: {} - {}", trace, dbResponse.getres_msg(), dbResponse.getres_code());
                if (!Objects.equals("00", dbResponse.getres_code())) {
                    errors.put(s.getDay(), dbResponse.getres_msg());
                }
            }
            if (errors.isEmpty()) {
                log.info("Trace <{}> - Actualización de horarios para tipo de recurso <{}> exitosa", trace, userId);
                response = APIResponseDTO.<Map<String, String>>builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_UPDATE.getMessage()).build();
            } else {
                log.info("Trace <{}> - Actualización de horarios para tipo de recurso <{}> realizada con errores", trace, userId);
                response = APIResponseDTO.<Map<String, String>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.SUCCESS_UPDATE_WITH_ERRORS.getMessage()).data(errors).build();
            }
            return response;
        } catch (Exception e) {
            response = APIResponseDTO.<Map<String, String>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - ", trace, e);
        }
        return response;
    }

}
