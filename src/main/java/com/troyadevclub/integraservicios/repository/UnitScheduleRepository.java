package com.troyadevclub.integraservicios.repository;

import com.troyadevclub.integraservicios.entity.UnitScheduleEntity;
import com.troyadevclub.integraservicios.entity.projection.DBGeneralResponseProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UnitScheduleRepository extends CrudRepository<UnitScheduleEntity, Integer> {

    @Query(value = "SELECT hu.* FROM \"HORARIO_UNIDAD\" hu, \"UNIDAD\" u " +
                        "WHERE hu.\"UNI_ID\" = u.\"UNI_ID\" " +
                        "AND u.\"USUA_ID\" = :usua_id :: INT ;", nativeQuery = true)
    List<UnitScheduleEntity> getUnitSchedules(@Param("usua_id") String usua_id);

    @Query(value = "SELECT * FROM \"public\".\"modify_unit_schedule\"(:start_time, :end_time, :day, :usua_id);", nativeQuery = true)
    DBGeneralResponseProjection updateUnitSchedule(@Param("start_time") String start_time,
                                                   @Param("end_time") String end_time,
                                                   @Param("day") String day,
                                                   @Param("usua_id") String usua_id);

}
