package com.troyadevclub.integraservicios.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class APIResponseDTO<T> {

    private Boolean status;

    private String message = "";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data = null;

}
