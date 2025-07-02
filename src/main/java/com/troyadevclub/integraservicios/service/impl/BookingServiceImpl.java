package com.troyadevclub.integraservicios.service.impl;

import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.dto.booking.CreateBookingRequestDTO;
import com.troyadevclub.integraservicios.dto.booking.GetAllBookingsProjection;
import com.troyadevclub.integraservicios.repository.BookingRepository;
import com.troyadevclub.integraservicios.service.BookingService;
import com.troyadevclub.integraservicios.utils.Constants;
import com.troyadevclub.integraservicios.utils.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class BookingServiceImpl implements BookingService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public APIResponseDTO<Object> create(CreateBookingRequestDTO request, HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<Object> response;
        try {
            String userId = Utils.getUserID(httpRequest);
            StoredProcedureQuery query = callCreateBooking(request, userId);
            String res_msg = (String) query.getOutputParameterValue("res_msg");
            String res_code = (String) query.getOutputParameterValue("res_code");
            if (res_msg.isBlank() || res_code.isBlank()) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_REGISTER.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - DB Response: {} - {}", trace, res_msg, res_code);
            if (!Objects.equals("00", res_code)) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_REGISTER.getMessage()).data(res_msg).build();
                return response;
            }
            log.info("Trace <{}> - Creación de reserva <{}> para unidad <{}> exitoso", trace, userId, userId);
            response = APIResponseDTO.builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_REGISTER.getMessage()).build();
        } catch (Exception e) {
            response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - ", trace, e);
        }
        return response;
    }

    @Override
    public APIResponseDTO<List<GetAllBookingsProjection>> getAll(HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<List<GetAllBookingsProjection>> response;
        try {
            List<GetAllBookingsProjection> projections = bookingRepository.getAll();
            if (Objects.isNull(projections) || projections.isEmpty()) {
                response = APIResponseDTO.<List<GetAllBookingsProjection>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.SUCCESS_OPERATION_WITHOUT_RESULTS.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - Consulta general de reservas exitosa", trace);
            response = APIResponseDTO.<List<GetAllBookingsProjection>>builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_OPERATION.getMessage()).data(projections).build();
        } catch (Exception e) {
            response = APIResponseDTO.<List<GetAllBookingsProjection>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - Error general - ", trace, e);
        }
        return response;
    }

    @Override
    public APIResponseDTO<GetAllBookingsProjection> getById(Integer bookingId, HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<GetAllBookingsProjection> response;
        try {
            GetAllBookingsProjection projection = bookingRepository.getById(bookingId);
            if (Objects.isNull(projection)) {
                response = APIResponseDTO.<GetAllBookingsProjection>builder().status(Boolean.FALSE).message(Constants.APIMessageType.SUCCESS_OPERATION_WITHOUT_RESULTS.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - Consulta de reserva por id <{}> exitosa", trace, bookingId);
            response = APIResponseDTO.<GetAllBookingsProjection>builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_OPERATION.getMessage()).data(projection).build();
        } catch (Exception e) {
            response = APIResponseDTO.<GetAllBookingsProjection>builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - Error general - ", trace, e);
        }
        return response;
    }

    @Override
    public APIResponseDTO<List<GetAllBookingsProjection>> getByUserId(HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<List<GetAllBookingsProjection>> response;
        try {
            String userId = Utils.getUserID(httpRequest);
            List<GetAllBookingsProjection> projection = bookingRepository.getByUserId(Integer.valueOf(userId));
            if (Objects.isNull(projection)) {
                response = APIResponseDTO.<List<GetAllBookingsProjection>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.SUCCESS_OPERATION_WITHOUT_RESULTS.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - Consulta de reserva por id de usuario <{}> exitosa", trace, userId);
            response = APIResponseDTO.<List<GetAllBookingsProjection>>builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_OPERATION.getMessage()).data(projection).build();
        } catch (Exception e) {
            response = APIResponseDTO.<List<GetAllBookingsProjection>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - Error general - ", trace, e);
        }
        return response;
    }

    private StoredProcedureQuery callCreateBooking(CreateBookingRequestDTO request, String userId) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("create_booking");
        query.registerStoredProcedureParameter("client_id", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("unit_user_id", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("info_text", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("start_time_text", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("end_time_text", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("res_msg", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("res_code", String.class, ParameterMode.OUT);
        query.setParameter("client_id", userId);
        query.setParameter("unit_user_id", request.getUnitId());
        query.setParameter("info_text", request.getResourceIds().toString());
        query.setParameter("start_time_text", request.getStartDate());
        query.setParameter("end_time_text", request.getEndDate());
        query.execute();
        return query;
    }

}
