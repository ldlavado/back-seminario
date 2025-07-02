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
@Table(name = "\"RESERVA\"", schema = "\"public\"")
public class BookingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "\"RESE_ID\"")
    private Integer RESE_ID;

    @Column(name = "\"USUA_CLIENTE\"")
    private Integer USUA_CLIENTE;

    @Column(name = "\"RESE_ESTADO\"")
    private String RESE_ESTADO;

    @Column(name = "\"RESE_CALIFICACION\"")
    private Integer RESE_CALIFICACION;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "\"RESE_FECHA_REGISTRO\"")
    private Date RESE_FECHA_REGISTRO;

    @Serial
    private static final long serialVersionUID = 5474376661739718937L;

}
