package com.troyadevclub.integraservicios.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;

@Data
public class LoginRequestDTO implements Serializable {

    @NotEmpty
    @Email(regexp = "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}", flags = Pattern.Flag.CASE_INSENSITIVE)
    @Size(min = 6, max = 100)
    private String email;

    @NotEmpty
    @Size(min = 6, max = 30)
    private String password;

    @Serial
    private static final long serialVersionUID = -5283377458306601010L;

}