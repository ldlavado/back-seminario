package com.troyadevclub.integraservicios.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public abstract class ErrorMessageDetail {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected String error;

}
