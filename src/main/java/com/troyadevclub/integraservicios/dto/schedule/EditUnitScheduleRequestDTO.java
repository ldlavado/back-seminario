package com.troyadevclub.integraservicios.dto.schedule;

import com.troyadevclub.integraservicios.dto.validators.correctTimes.CorrectTimes;
import com.troyadevclub.integraservicios.dto.validators.uniqueDay.UniqueDay;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class EditUnitScheduleRequestDTO implements Serializable {

    @Valid
    @NotEmpty
    @Size.List({
            @Size(min = 1, message = "Debe tener al menos un horario"),
            @Size(max = 7, message = "No puede editar más de 7 horarios")
    })
    @UniqueDay
    private List<Schedule> schedules;

    @Serial
    private static final long serialVersionUID = -7525328858785992610L;

    @Valid
    @CorrectTimes
    @Data
    public static class Schedule implements Serializable {

        @NotEmpty
        @Pattern(regexp = "^(lunes|martes|miercoles|jueves|viernes|sabado|domingo)$", message = "Día inválido", flags = Pattern.Flag.CASE_INSENSITIVE)
        private String day;

        @NotEmpty
        @Pattern(regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$", message = "Hora de comienzo inválida")
        private String startTime;

        @NotEmpty
        @Pattern(regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$", message = "Hora de finalización inválida")
        private String endTime;

        @Serial
        private static final long serialVersionUID = 1757793904570674516L;

    }

}
