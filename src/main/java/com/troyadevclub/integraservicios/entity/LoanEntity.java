package com.troyadevclub.integraservicios.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "\"PRESTAMO\"", schema = "\"public\"")
public class LoanEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "\"PRES_ID\"")
    private Integer PRES_ID;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "\"PRES_FECHA_ENTREGA\"")
    private Date PRES_FECHA_ENTREGA;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "\"PRES_FECHA_DEVOLUCION\"")
    private Date PRES_FECHA_DEVOLUCION;

    @Column(name = "\"PRES_ENTREGA_EMPLEADO\"")
    private Integer PRES_ENTREGA_EMPLEADO;

    @Column(name = "\"PRES_DEVOLUCION_EMPLEADO\"")
    private Integer PRES_DEVOLUCION_EMPLEADO;

    @Column(name = "\"PRES_DETALLE\"")
    private String PRES_DETALLE;

    @Column(name = "\"RESE_ID\"")
    private Integer RESE_ID;

    private static final long serialVersionUID = -1774572535508885298L;

}
