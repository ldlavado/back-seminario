package com.troyadevclub.integraservicios.repository;

import com.troyadevclub.integraservicios.entity.ResourceTypeScheduleEntity;
import com.troyadevclub.integraservicios.entity.projection.DBGeneralResponseProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceTypeScheduleRepository extends CrudRepository<ResourceTypeScheduleEntity, Integer> {

    @Query(value = "SELECT htr.* FROM \"HORARIO_TIPO_RECURSO\" htr " +
                    "JOIN \"UNIDAD_TIPO_RECURSO\" utr ON htr.\"UNI_ID\" = utr.\"UNI_ID\" " +
                    "AND htr.\"TIRE_ID\" = utr.\"TIRE_ID\" " +
                    "JOIN \"UNIDAD\" u ON utr.\"UNI_ID\" = u.\"UNI_ID\" " +
                    "WHERE u.\"USUA_ID\" = :usua_id :: INT " +
                    "AND htr.\"TIRE_ID\" = :resource_type_id :: INT ;", nativeQuery = true)
    List<ResourceTypeScheduleEntity> getResourceTypeSchedules(@Param("usua_id") String usua_id,
                                                      @Param("resource_type_id") String resource_type_id);

    @Query(value = "SELECT * FROM \"public\".\"modify_resource_type_schedule\"(:start_time, :end_time, :day, :resource_type, :usua_id);", nativeQuery = true)
    DBGeneralResponseProjection updateResourceTypeSchedule(@Param("start_time") String start_time,
                            @Param("end_time") String end_time,
                            @Param("day") String day,
                            @Param("resource_type") String resource_type,
                            @Param("usua_id") String usua_id);

}
