package com.troyadevclub.integraservicios.entity.id;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ResourceTypeScheduleEntityId implements Serializable {

    private Integer UNI_ID;

    private Integer TIRE_ID;

    private String HOTI_DIA;

    @Serial
    private static final long serialVersionUID = 6008327490712275263L;

}