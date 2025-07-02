package com.troyadevclub.integraservicios.dto.booking;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

public interface GetAllBookingsProjection {

    public Integer getRESE_ID();

    public Integer getUSUA_CLIENTE();

    public String getRESE_ESTADO();

    public Integer getRESE_CALIFICACION();

    @Temporal(TemporalType.TIMESTAMP)
    public Date getRESE_FECHA_REGISTRO();

    public String getUSUA_NOMBRES();

    public String getUSUA_APELLIDOS();

    public String getUSUA_CORREO();

    public String getRECU_NOMBRE();

}
