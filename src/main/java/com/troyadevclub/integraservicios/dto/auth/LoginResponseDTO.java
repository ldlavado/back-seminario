package com.troyadevclub.integraservicios.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.troyadevclub.integraservicios.dto.ErrorMessageDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseDTO extends ErrorMessageDetail implements Serializable {

    private String token;

    @Serial
    private static final long serialVersionUID = 6271504992821434435L;

}
