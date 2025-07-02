package com.troyadevclub.integraservicios.repository;

import com.troyadevclub.integraservicios.dto.feature.GetByResourceTypeProjection;
import com.troyadevclub.integraservicios.entity.ResourceEntity;
import com.troyadevclub.integraservicios.entity.projection.DBGeneralResponseProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends CrudRepository<ResourceEntity, Integer> {

    @Query(value = "SELECT * FROM \"public\".\"create_resource\"(:name, :resourceTypeId);", nativeQuery = true)
    DBGeneralResponseProjection create(@Param("name") String name,
                                       @Param("resourceTypeId") String resourceTypeId);

    @Override
    List<ResourceEntity> findAll();

    @Query(value = "SELECT r.*, c.\"CARA_NOMBRE\", c.\"CARA_DESCRIPCION\" " +
            "FROM \"RECURSO\" r " +
            "JOIN \"UNIDAD_RECURSO_CARACTERISTICA\" urc ON r.\"RECU_ID\" = urc.\"RECU_ID\" " +
            "JOIN \"CARACTERISTICA\" c ON urc.\"CARA_ID\" = c.\"CARA_ID\" " +
            "WHERE r.\"TIRE_ID\" = :resourceTypeId;", nativeQuery = true)
    List<GetByResourceTypeProjection> findAllByTIREID(@Param("resourceTypeId") Integer resourceTypeId);

    @Query(value = "SELECT * FROM \"public\".\"associate_resource\"(:resource_id, :quantity, :usua_id);", nativeQuery = true)
    DBGeneralResponseProjection associateResource(@Param("resource_id") String resource_id,
                                                  @Param("quantity") String quantity,
                                                  @Param("usua_id") String usua_id);

}
