package com.troyadevclub.integraservicios.dto.validators.uniqueDay;

import com.troyadevclub.integraservicios.dto.schedule.EditUnitScheduleRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniqueDayValidator implements ConstraintValidator<UniqueDay, List<EditUnitScheduleRequestDTO.Schedule>> {

    @Override
    public boolean isValid(List<EditUnitScheduleRequestDTO.Schedule> schedules, ConstraintValidatorContext context) {
        Set<String> days = new HashSet<>();
        for (EditUnitScheduleRequestDTO.Schedule schedule : schedules) {
            if (!days.add(schedule.getDay().toUpperCase())) {
                return false;
            }
        }
        return true;
    }
}