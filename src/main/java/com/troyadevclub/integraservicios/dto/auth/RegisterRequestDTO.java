package com.troyadevclub.integraservicios.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class RegisterRequestDTO implements Serializable {

    @Size(min = 4, max = 100)
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z ]+", message = "Solo valores alfanuméricos")
    private String name;

    @Size(min = 4, max = 100)
    @Pattern(regexp = "[a-zA-Z]+", message = "Solo valores alfanuméricos")
    private String lastName;

    @Size(min = 10, max = 10)
    @NotEmpty
    @Pattern(regexp = "[0-9]+", message = "Solo valores numéricos")
    private String phoneNumber;

    @Size(min = 6, max = 100)
    @Email(regexp = "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}", flags = Pattern.Flag.CASE_INSENSITIVE)
    @NotEmpty
    private String email;

    @Size(min = 5, max = 100)
    private String address;

    @Size(min = 6, max = 30)
    @NotEmpty
    private String password;

    @NotEmpty
    @Pattern(regexp = "cliente|empleado|unidad", message = "Solo se aceptan los valores cliente | empleado | unidad")
    private String userType;

    @Pattern(regexp = "deportiva|administrativa|laboratorio|otro", message = "Solo se aceptan los valores deportiva | administrativa | laboratorio | otro")
    private String unitType;

    @NotEmpty
    @Pattern(regexp = "interna|externa", message = "Solo se aceptan los valores interna | externa")
    private String integrationType;

    @Serial
    private static final long serialVersionUID = 4904442030124467540L;

}