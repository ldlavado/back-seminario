package com.troyadevclub.integraservicios.entity;

import com.troyadevclub.integraservicios.entity.id.FeatureAssociationEntityId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FeatureAssociationEntityId.class)
@Table(name = "\"UNIDAD_TIPO_RECURSO_CARACTERISTICA\"")
public class FeatureAssociationEntity implements Serializable {

    @Id
    @Column(name = "\"UNI_ID\"")
    private Integer UNI_ID;

    @Id
    @Column(name = "\"TIRE_ID\"")
    private Integer TIRE_ID;

    @Id
    @Column(name = "\"CARA_ID\"")
    private Integer CARA_ID;

    @Column(name = "\"UTI_VALOR\"")
    private String UTI_VALOR;

    @Serial
    private static final long serialVersionUID = 5474376661739718937L;

}
