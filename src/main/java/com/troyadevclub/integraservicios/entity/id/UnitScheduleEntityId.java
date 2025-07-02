package com.troyadevclub.integraservicios.entity.id;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UnitScheduleEntityId implements Serializable {

    private Integer UNI_ID;

    private String HOUN_DIA;

    @Serial
    private static final long serialVersionUID = -5618861452022620443L;

}