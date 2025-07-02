package com.troyadevclub.integraservicios.dto.resourceType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CreateResourceTypeRequestDTO implements Serializable {

    @NotEmpty
    @Size(min = 5, max = 100)
    private String name;

    @NotEmpty
    @Size(min = 5, max = 100)
    private String description;

    @Serial
    private static final long serialVersionUID = 6692776486527381024L;

}
