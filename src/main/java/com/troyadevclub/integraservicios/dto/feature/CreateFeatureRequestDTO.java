package com.troyadevclub.integraservicios.dto.feature;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CreateFeatureRequestDTO implements Serializable {

    @NotEmpty
    @Size(min = 1, max = 250)
    private String name;

    @NotEmpty
    @Size(min = 1, max = 250)
    private String description;

    @Serial
    private static final long serialVersionUID = -658394928253371904L;

}
