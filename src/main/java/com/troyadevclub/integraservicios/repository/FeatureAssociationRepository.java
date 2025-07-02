package com.troyadevclub.integraservicios.repository;

import com.troyadevclub.integraservicios.dto.feature.GetAssociationsProjection;
import com.troyadevclub.integraservicios.entity.FeatureAssociationEntity;
import com.troyadevclub.integraservicios.entity.id.FeatureAssociationEntityId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeatureAssociationRepository extends CrudRepository<FeatureAssociationEntity, FeatureAssociationEntityId> {

    @Query(value = "SELECT urc.*, c.\"CARA_NOMBRE\", c.\"CARA_DESCRIPCION\" FROM \"public\".\"UNIDAD_RECURSO_CARACTERISTICA\" urc " +
            "JOIN \"public\".\"CARACTERISTICA\" c " +
            "ON urc.\"CARA_ID\" = c.\"CARA_ID\" " +
            "JOIN \"public\".\"RECURSO\" r " +
            "ON urc.\"RECU_ID\" = r.\"RECU_ID\" " +
            "JOIN \"public\".\"TIPO_RECURSO\" tr " +
            "ON r.\"TIRE_ID\" = tr.\"TIRE_ID\" " +
            "JOIN \"public\".\"UNIDAD_TIPO_RECURSO\" utr " +
            "ON tr.\"TIRE_ID\" = utr.\"TIRE_ID\" " +
            "JOIN \"public\".\"UNIDAD\" u " +
            "ON utr.\"UNI_ID\" = u.\"UNI_ID\" " +
            "WHERE u.\"USUA_ID\" = :userId :: INTEGER " +
            "AND r.\"RECU_ID\" = :resourceId ::INTEGER;", nativeQuery = true)
    List<GetAssociationsProjection> getAssociations(@Param("userId") String userId,
                                                    @Param("resourceId") String resourceId);

}
