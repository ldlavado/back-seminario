package com.troyadevclub.integraservicios.dto.schedule;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class EditResourceTypeScheduleRequestDTO extends EditUnitScheduleRequestDTO implements Serializable {

    @NotEmpty
    @Size(min = 1, max = 3)
    private String resourceTypeId;

    @Serial
    private static final long serialVersionUID = 6624530435449075777L;

}
