package com.troyadevclub.integraservicios.repository;

import com.troyadevclub.integraservicios.entity.FeatureEntity;
import com.troyadevclub.integraservicios.entity.projection.DBGeneralResponseProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeatureRepository extends CrudRepository<FeatureEntity, Integer> {

    @Query(value = "SELECT * FROM \"public\".\"create_feature\"(:name, :description);", nativeQuery = true)
    DBGeneralResponseProjection create(@Param("name") String name,
                                       @Param("description") String description);

    @Override
    List<FeatureEntity> findAll();

    @Query(value = "SELECT * FROM \"public\".\"associate_feature\"(:featureId, :resourceId, :userId, :value);", nativeQuery = true)
    DBGeneralResponseProjection associateFeature(@Param("featureId") String featureId,
                                                 @Param("resourceId") String resourceId,
                                                 @Param("userId") String userId,
                                                 @Param("value") String value);

    @Query(value = "WITH a AS ( " +
            "SELECT \"RECU_ID\", " +
            "CONCAT('[', STRING_AGG('\"' || htr.\"HOTI_DIA\" || ' ' || htr.\"HOTI_HORA_COMIENZO\" || ' ' || htr.\"HOTI_HORA_FIN\" || '\"', ','), ']')::jsonb horario_disponibilidad " +
            "FROM \"RECURSO\" r " +
            "JOIN \"TIPO_RECURSO\" tr ON r.\"TIRE_ID\" = tr.\"TIRE_ID\" " +
            "JOIN \"UNIDAD_TIPO_RECURSO\" utr ON tr.\"TIRE_ID\" = utr.\"TIRE_ID\" " +
            "JOIN \"HORARIO_TIPO_RECURSO\" htr ON utr.\"TIRE_ID\" = htr.\"TIRE_ID\" " +
            "WHERE utr.\"UNI_ID\" = :id " +
            "AND htr.\"UNI_ID\" = :id " +
            "GROUP BY \"RECU_ID\" " +
            "), b AS ( " +
            "SELECT r.\"RECU_ID\" id_recurso, r.\"RECU_NOMBRE\" nombre, tr.\"TIRE_NOMBRE\" tipo_recurso, a.horario_disponibilidad " +
            "FROM \"RECURSO\" r " +
            "JOIN a ON r.\"RECU_ID\" = a.\"RECU_ID\" " +
            "JOIN \"TIPO_RECURSO\" tr ON r.\"TIRE_ID\" = tr.\"TIRE_ID\" " +
            "GROUP BY r.\"RECU_ID\", r.\"RECU_NOMBRE\", tr.\"TIRE_NOMBRE\", a.horario_disponibilidad " +
            ") " +
            "SELECT jsonb_build_object('recursos_disponibles', jsonb_agg(b.*))::text FROM b;", nativeQuery = true)
    String getExternalData(@Param("id") Integer id);

}
