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
@Table(name = "\"RECURSO\"", schema = "\"public\"")
public class ResourceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "\"RECU_ID\"")
    private Integer RECU_ID;

    @Column(name = "\"RECU_NOMBRE\"")
    private String RECU_NOMBRE;

    @Column(name = "\"TIRE_ID\"")
    private Integer TIREID;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "\"RECU_FECHA_REGISTRO\"")
    private Date RECU_FECHA_REGISTRO;

    @Serial
    private static final long serialVersionUID = 195393429682673747L;

}
