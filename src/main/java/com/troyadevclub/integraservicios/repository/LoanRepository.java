package com.troyadevclub.integraservicios.repository;

import com.troyadevclub.integraservicios.dto.loan.GetAllLoansProjection;
import com.troyadevclub.integraservicios.entity.LoanEntity;
import com.troyadevclub.integraservicios.entity.projection.DBGeneralResponseProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends CrudRepository<LoanEntity, Long> {

    @Query(value = "SELECT * FROM \"public\".\"create_loan\"(:user_id, :booking_id, :detail);",nativeQuery = true)
    DBGeneralResponseProjection createLoan(@Param("user_id") String user_id,
                                           @Param("booking_id") String booking_id,
                                           @Param("detail") String detail);

    @Query(value = "SELECT * FROM \"public\".\"return_loan\"(:user_id, :loan_id, :detail, :score);",nativeQuery = true)
    DBGeneralResponseProjection returnLoan(@Param("user_id") String user_id,
                                           @Param("loan_id") String loan_id,
                                           @Param("detail") String detail,
                                           @Param("score") String score);

    @Query(value = "SELECT p.\"PRES_ID\", p.\"PRES_FECHA_ENTREGA\", p.\"PRES_FECHA_DEVOLUCION\", p.\"PRES_DETALLE\"," +
            "JSONB_AGG(" +
            "JSONB_BUILD_OBJECT(" +
            "'entregaEmpId', e1.\"EMP_ID\", 'entregaNombre', u1.\"USUA_NOMBRES\", 'entregaApellido'," + "u1.\"USUA_APELLIDOS\"," +
            "'devolucionEmpId', e2.\"EMP_ID\", 'devolucionNombre', u2.\"USUA_NOMBRES\", 'devolucionApellido', u2.\"USUA_APELLIDOS\"" +
            ")) AS \"employeesData\"" +
            "FROM \"PRESTAMO\" p " +
            "LEFT JOIN \"EMPLEADO\" e1 ON p.\"PRES_ENTREGA_EMPLEADO\" = e1.\"EMP_ID\" " +
            "LEFT JOIN \"USUARIO\" u1 ON e1.\"USUA_ID\" = u1.\"USUA_ID\" " +
            "LEFT JOIN \"EMPLEADO\" e2 ON p.\"PRES_DEVOLUCION_EMPLEADO\" = e2.\"EMP_ID\" " +
            "LEFT JOIN \"USUARIO\" u2 ON e2.\"USUA_ID\" = u2.\"USUA_ID\" " +
            "GROUP BY p.\"PRES_ID\";", nativeQuery = true)
    List<GetAllLoansProjection> getAll();

    @Query(value = "SELECT p.\"PRES_ID\", p.\"PRES_FECHA_ENTREGA\", p.\"PRES_FECHA_DEVOLUCION\", p.\"PRES_DETALLE\", " +
            "JSONB_AGG(" +
            "JSONB_BUILD_OBJECT(" +
            "'entregaEmpId', e1.\"EMP_ID\", 'entregaNombre', u1.\"USUA_NOMBRES\", 'entregaApellido'," + "u1.\"USUA_APELLIDOS\"," +
            "'devolucionEmpId', e2.\"EMP_ID\", 'devolucionNombre', u2.\"USUA_NOMBRES\", 'devolucionApellido', u2.\"USUA_APELLIDOS\"" +
            ")) AS \"employeesData\"" +
            "FROM \"PRESTAMO\" p " +
            "LEFT JOIN \"EMPLEADO\" e1 ON p.\"PRES_ENTREGA_EMPLEADO\" = e1.\"EMP_ID\" " +
            "LEFT JOIN \"USUARIO\" u1 ON e1.\"USUA_ID\" = u1.\"USUA_ID\" " +
            "LEFT JOIN \"EMPLEADO\" e2 ON p.\"PRES_DEVOLUCION_EMPLEADO\" = e2.\"EMP_ID\" " +
            "LEFT JOIN \"USUARIO\" u2 ON e2.\"USUA_ID\" = u2.\"USUA_ID\" " +
            "JOIN \"RESERVA\" r ON p.\"RESE_ID\" = r.\"RESE_ID\" " +
            "WHERE r.\"RESE_ID\" = :bookingId " +
            "GROUP BY p.\"PRES_ID\";", nativeQuery = true)
    List<GetAllLoansProjection> getByBookingId(@Param("bookingId") Integer bookingId);

}
