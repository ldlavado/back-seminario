package com.troyadevclub.integraservicios.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"CARACTERISTICA\"", schema = "\"public\"")
public class FeatureEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "\"CARA_ID\"")
    private Integer CARA_ID;

    @Column(name = "\"CARA_NOMBRE\"")
    private String CARA_NOMBRE;

    @Column(name = "\"CARA_DESCRIPCION\"")
    private String CARA_DESCRIPCION;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "\"CARA_FECHA_REGISTRO\"")
    private Date CARA_FECHA_REGISTRO;

    @Serial
    private static final long serialVersionUID = 5474376661739718937L;

}
