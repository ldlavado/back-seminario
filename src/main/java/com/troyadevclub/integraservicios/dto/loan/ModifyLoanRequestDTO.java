package com.troyadevclub.integraservicios.dto.loan;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ModifyLoanRequestDTO implements Serializable {

    @Size(min = 1, max = 250)
    private String detail;

    @Serial
    private static final long serialVersionUID = 6544510790159388790L;

}
