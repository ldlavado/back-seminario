package com.troyadevclub.integraservicios.entity;

import com.troyadevclub.integraservicios.entity.id.ResourceTypeScheduleEntityId;
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
@IdClass(ResourceTypeScheduleEntityId.class)
@Table(name = "\"HORARIO_TIPO_RECURSO\"", schema = "\"public\"")
public class ResourceTypeScheduleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "\"UNI_ID\"")
    private Integer UNI_ID;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "\"TIRE_ID\"")
    private Integer TIRE_ID;

    @Id
    @Column(name = "\"HOTI_DIA\"")
    private String HOTI_DIA;

    @Temporal(TemporalType.TIME)
    @Column(name = "\"HOTI_HORA_COMIENZO\"")
    private Time HOTI_HORA_COMIENZO;

    @Temporal(TemporalType.TIME)
    @Column(name = "\"HOTI_HORA_FIN\"")
    private Time HOTI_HORA_FIN;

    @Serial
    private static final long serialVersionUID = 3662015153385150004L;

}
