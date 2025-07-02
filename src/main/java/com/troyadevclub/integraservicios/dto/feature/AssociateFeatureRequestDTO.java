package com.troyadevclub.integraservicios.dto.feature;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AssociateFeatureRequestDTO implements Serializable {

    @NotEmpty
    @Size(min = 1, max = 3)
    private String featureId;

    @NotEmpty
    @Size(min = 1, max = 3)
    private String resourceId;

    @NotEmpty
    @Length(min = 1, max = 100)
    private String value;

    @Serial
    private static final long serialVersionUID = -6307217287685818052L;

}
