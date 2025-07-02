package com.troyadevclub.integraservicios.entity.id;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class FeatureAssociationEntityId implements Serializable {

    private Integer UNI_ID;

    private Integer TIRE_ID;

    private Integer CARA_ID;

    @Serial
    private static final long serialVersionUID = 3571348167651161319L;

}
