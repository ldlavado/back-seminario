package com.troyadevclub.integraservicios.entity;

import com.troyadevclub.integraservicios.entity.id.UnitScheduleEntityId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Time;

@Data
@Entity
@IdClass(UnitScheduleEntityId.class)
@Table(name = "\"HORARIO_UNIDAD\"", schema = "\"public\"")
public class UnitScheduleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "\"UNI_ID\"")
    private Integer UNI_ID;

    @Id
    @Column(name = "\"HOUN_DIA\"")
    private String HOUN_DIA;

    @Temporal(TemporalType.TIME)
    @Column(name = "\"HOUN_HORA_COMIENZO\"")
    private Time HOUN_HORA_COMIENZO;

    @Temporal(TemporalType.TIME)
    @Column(name = "\"HOUN_HORA_FIN\"")
    private Time HOUN_HORA_FIN;

    @Serial
    private static final long serialVersionUID = -6686129182564853564L;

}
