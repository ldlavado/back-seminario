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
@Table(name = "\"TIPO_RECURSO\"", schema = "\"public\"")
public class ResourceTypeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "\"TIRE_ID\"")
    private Integer TIRE_ID;

    @Column(name = "\"TIRE_NOMBRE\"")
    private String TIRE_NOMBRE;

    @Column(name = "\"TIRE_DESCRIPCION\"")
    private String TIRE_DESCRIPCION;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "\"TIRE_FECHA_REGISTRO\"")
    private Date TIRE_FECHA_REGISTRO;

    @Serial
    private static final long serialVersionUID = 195393429682673747L;

}
