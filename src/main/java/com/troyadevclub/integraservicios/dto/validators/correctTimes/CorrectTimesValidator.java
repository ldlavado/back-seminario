package com.troyadevclub.integraservicios.dto.validators.correctTimes;

import com.troyadevclub.integraservicios.dto.schedule.EditUnitScheduleRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CorrectTimesValidator implements ConstraintValidator<CorrectTimes, EditUnitScheduleRequestDTO.Schedule> {

    @Override
    public boolean isValid(EditUnitScheduleRequestDTO.Schedule schedule, ConstraintValidatorContext context) {
        if (schedule == null) {
            return true;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime startTime = LocalTime.parse(schedule.getStartTime(), formatter);
            LocalTime endTime = LocalTime.parse(schedule.getEndTime(), formatter);
            return !startTime.isAfter(endTime);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}