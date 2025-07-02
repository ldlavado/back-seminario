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

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "\"USUARIO\"", schema = "\"public\"")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "\"USUA_ID\"")
    private Integer USUA_ID;

    @Column(name = "\"USUA_NOMBRES\"")
    private String USUA_NOMBRES;

    @Column(name = "\"USUA_APELLIDOS\"")
    private String USUA_APELLIDOS;

    @Column(name = "\"USUA_TELEFONO\"")
    private String USUA_TELEFONO;

    @Column(name = "\"USUA_CORREO\"")
    private String USUA_CORREO;

    @Column(name = "\"USUA_DIRECCION\"")
    private String USUA_DIRECCION;

    @Column(name = "\"USUA_CONTRA\"")
    private String USUA_CONTRA;

    @Temporal(TemporalType.TIME)
    @Column(name = "\"USUA_FECHA_REGISTRO\"")
    private Date USUA_FECHA_REGISTRO;

    @Serial
    private static final long serialVersionUID = 1009985126759230756L;

}
