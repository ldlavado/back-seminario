package com.troyadevclub.integraservicios.repository;

import com.troyadevclub.integraservicios.entity.ResourceTypeEntity;
import com.troyadevclub.integraservicios.entity.projection.DBGeneralResponseProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceTypeRepository extends CrudRepository<ResourceTypeEntity, Integer> {

    @Query(value = "SELECT * FROM \"public\".\"create_resource_type\"(:name, :description);", nativeQuery = true)
    DBGeneralResponseProjection create(@Param("name") String name,
                                          @Param("description") String description);

    @Query(value = "SELECT tr.* " +
            "FROM \"TIPO_RECURSO\" tr " +
            "JOIN \"UNIDAD_TIPO_RECURSO\" utr ON utr.\"TIRE_ID\" = tr.\"TIRE_ID\" " +
            "JOIN \"UNIDAD\" u ON u.\"UNI_ID\" = utr.\"UNI_ID\" " +
            "WHERE u.\"USUA_ID\" = :user_id ::int;", nativeQuery = true)
    List<ResourceTypeEntity> getByUnit(@Param("user_id") String user_id);

    @Query(value = "SELECT * FROM \"public\".\"associate_resource_type\"(:resource_type_id, :usua_id);", nativeQuery = true)
    DBGeneralResponseProjection associateResourceType(@Param("resource_type_id") String resource_type_id,
                                       @Param("usua_id") String usua_id);

    @Override
    List<ResourceTypeEntity> findAll();

}
