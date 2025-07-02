package com.troyadevclub.integraservicios.repository;

import com.troyadevclub.integraservicios.dto.booking.GetAllBookingsProjection;
import com.troyadevclub.integraservicios.entity.BookingEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends CrudRepository<BookingEntity, Integer> {

    @Query(value = "SELECT r.*, u.\"USUA_NOMBRES\", u.\"USUA_APELLIDOS\", u.\"USUA_CORREO\", rec.\"RECU_NOMBRE\" " +
            "FROM \"RESERVA\" r " +
            "JOIN \"USUARIO\" u ON r.\"USUA_CLIENTE\" = u.\"USUA_ID\" " +
            "JOIN \"ITEM_RESERVA\" ir ON r.\"RESE_ID\" = ir.\"RESE_ID\" " +
            "JOIN \"INVENTARIO\" i ON ir.\"INV_ID\" = i.\"INV_ID\" " +
            "JOIN \"RECURSO\" rec ON i.\"RECU_ID\" = rec.\"RECU_ID\";", nativeQuery = true)
    List<GetAllBookingsProjection> getAll();

    @Query(value = "SELECT r.*, u.\"USUA_NOMBRES\", u.\"USUA_APELLIDOS\", u.\"USUA_CORREO\", rec.\"RECU_NOMBRE\" " +
            "FROM \"RESERVA\" r " +
            "JOIN \"USUARIO\" u ON r.\"USUA_CLIENTE\" = u.\"USUA_ID\" " +
            "JOIN \"ITEM_RESERVA\" ir ON r.\"RESE_ID\" = ir.\"RESE_ID\" " +
            "JOIN \"INVENTARIO\" i ON ir.\"INV_ID\" = i.\"INV_ID\" " +
            "JOIN \"RECURSO\" rec ON i.\"RECU_ID\" = rec.\"RECU_ID\" " +
            "WHERE r.\"RESE_ID\" = :booking_id;", nativeQuery = true)
    GetAllBookingsProjection getById(@Param("booking_id") Integer bookingId);

    @Query(value = "SELECT r.*, u.\"USUA_NOMBRES\", u.\"USUA_APELLIDOS\", u.\"USUA_CORREO\", rec.\"RECU_NOMBRE\" " +
            "FROM \"RESERVA\" r " +
            "JOIN \"USUARIO\" u ON r.\"USUA_CLIENTE\" = u.\"USUA_ID\" " +
            "JOIN \"ITEM_RESERVA\" ir ON r.\"RESE_ID\" = ir.\"RESE_ID\" " +
            "JOIN \"INVENTARIO\" i ON ir.\"INV_ID\" = i.\"INV_ID\" " +
            "JOIN \"RECURSO\" rec ON i.\"RECU_ID\" = rec.\"RECU_ID\" " +
            "WHERE u.\"USUA_ID\" = :user_id;", nativeQuery = true)
    List<GetAllBookingsProjection> getByUserId(@Param("user_id") Integer user_id);

}
