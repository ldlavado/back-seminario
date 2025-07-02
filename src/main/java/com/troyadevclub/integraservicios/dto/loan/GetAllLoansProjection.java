package com.troyadevclub.integraservicios.dto.loan;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

public interface GetAllLoansProjection {

    public Integer getPRES_ID();

    @Temporal(TemporalType.TIMESTAMP)
    public Date getPRES_FECHA_ENTREGA();

    @Temporal(TemporalType.TIMESTAMP)
    public Date getPRES_FECHA_DEVOLUCION();

    public String getPRES_DETALLE();

    public Object getEmployeesData();

}
