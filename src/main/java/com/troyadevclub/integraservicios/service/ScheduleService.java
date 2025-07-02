package com.troyadevclub.integraservicios.service;

import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.dto.schedule.EditResourceTypeScheduleRequestDTO;
import com.troyadevclub.integraservicios.dto.schedule.EditUnitScheduleRequestDTO;
import com.troyadevclub.integraservicios.entity.ResourceTypeScheduleEntity;
import com.troyadevclub.integraservicios.entity.UnitScheduleEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public interface ScheduleService {

    APIResponseDTO<List<UnitScheduleEntity>> getUnitSchedule(HttpServletRequest httpRequest, String trace);

    APIResponseDTO<Map<String, String>> editUnitSchedule(EditUnitScheduleRequestDTO request, HttpServletRequest httpRequest, String trace);

    APIResponseDTO<List<ResourceTypeScheduleEntity>> getResourceTypeSchedule(String unitUserId, String resourceTypeId, HttpServletRequest httpRequest, String trace);

    APIResponseDTO<Map<String, String>> editResourceTypeSchedule(EditResourceTypeScheduleRequestDTO request, HttpServletRequest httpRequest, String trace);

}
