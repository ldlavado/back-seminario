package com.troyadevclub.integraservicios.repository;

import com.troyadevclub.integraservicios.entity.projection.DBGeneralResponseProjection;
import com.troyadevclub.integraservicios.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    @Query(value = "SELECT * FROM \"public\".\"register_user\"(:names, :last_names, :phone_number, :email, :address, :pass, :user_type, :unit_type, :integration_type);", nativeQuery = true)
    DBGeneralResponseProjection registerUser(@Param("names") String names,
                                             @Param("last_names") String last_names,
                                             @Param("phone_number") String phone_number,
                                             @Param("email") String email,
                                             @Param("address") String address,
                                             @Param("pass") String pass,
                                             @Param("user_type") String user_type,
                                             @Param("unit_type") String unit_type,
                                             @Param("integration_type") String integration_type);

    @Query(value = "SELECT * FROM \"public\".\"login_user\"(:email, :pass);", nativeQuery = true)
    DBGeneralResponseProjection loginUser(@Param("email") String email,
                                          @Param("pass") String pass);

    @Query(value = "SELECT * FROM \"public\".\"get_users\"(:user_type);", nativeQuery = true)
    String getUsers(@Param("user_type") String user_type);

    @Query(value = "SELECT * FROM \"public\".\"unlock_user\"(:user_id);", nativeQuery = true)
    DBGeneralResponseProjection unlockUser(@Param("user_id") Integer user_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE \"public\".\"UNIDAD\" SET \"UNI_TIEMPO_MINIMO_RESERVA\" = :m WHERE \"USUA_ID\" = :user_id;", nativeQuery = true)
    Integer updateUser(@Param("m") Integer m,
                       @Param("user_id") Integer userId);

}
