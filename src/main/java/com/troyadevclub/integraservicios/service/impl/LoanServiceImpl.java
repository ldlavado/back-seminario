package com.troyadevclub.integraservicios.service.impl;

import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.dto.loan.ModifyLoanRequestDTO;
import com.troyadevclub.integraservicios.dto.loan.GetAllLoansProjection;
import com.troyadevclub.integraservicios.entity.projection.DBGeneralResponseProjection;
import com.troyadevclub.integraservicios.repository.LoanRepository;
import com.troyadevclub.integraservicios.service.LoanService;
import com.troyadevclub.integraservicios.utils.Constants;
import com.troyadevclub.integraservicios.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public APIResponseDTO<Object> createLoan(ModifyLoanRequestDTO request, String bookingId, HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<Object> response;
        try {
            String userId = Utils.getUserID(httpRequest);
            DBGeneralResponseProjection dbResponse = loanRepository.createLoan(userId, bookingId, request.getDetail());
            if (Objects.isNull(dbResponse) || dbResponse.getres_msg().isBlank() || dbResponse.getres_code().isBlank()) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_REGISTER.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - DB Response: {} - {}", trace, dbResponse.getres_msg(), dbResponse.getres_code());
            if (!Objects.equals("00", dbResponse.getres_code())) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_REGISTER.getMessage()).data(dbResponse.getres_msg()).build();
                return response;
            }
            log.info("Trace <{}> - Creación de préstamo con reserva <{}> con empleado <{}> exitoso", trace, bookingId, userId);
            response = APIResponseDTO.builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_REGISTER.getMessage()).build();
        } catch (Exception e) {
            response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - ", trace, e);
        }
        return response;
    }

    @Override
    public APIResponseDTO<Object> returnLoan(ModifyLoanRequestDTO request, String loanId, String score, HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<Object> response;
        try {
            String userId = Utils.getUserID(httpRequest);
            DBGeneralResponseProjection dbResponse = loanRepository.returnLoan(userId, loanId, request.getDetail(), score);
            if (Objects.isNull(dbResponse) || dbResponse.getres_msg().isBlank() || dbResponse.getres_code().isBlank()) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_REGISTER.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - DB Response: {} - {}", trace, dbResponse.getres_msg(), dbResponse.getres_code());
            if (!Objects.equals("00", dbResponse.getres_code())) {
                response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.FAILED_REGISTER.getMessage()).data(dbResponse.getres_msg()).build();
                return response;
            }
            log.info("Trace <{}> - Devolución de préstamo <{}> con empleado <{}> exitoso", trace, loanId, userId);
            response = APIResponseDTO.builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_REGISTER.getMessage()).build();
        } catch (Exception e) {
            response = APIResponseDTO.builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - ", trace, e);
        }
        return response;
    }

    @Override
    public APIResponseDTO<List<GetAllLoansProjection>> getAll(HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<List<GetAllLoansProjection>> response;
        try {
            List<GetAllLoansProjection> projections = loanRepository.getAll();
            if (Objects.isNull(projections) || projections.isEmpty()) {
                response = APIResponseDTO.<List<GetAllLoansProjection>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.SUCCESS_OPERATION_WITHOUT_RESULTS.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - Consulta general de préstamos exitosa", trace);
            response = APIResponseDTO.<List<GetAllLoansProjection>>builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_OPERATION.getMessage()).data(projections).build();
        } catch (Exception e) {
            response = APIResponseDTO.<List<GetAllLoansProjection>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - Error general - ", trace, e);
        }
        return response;
    }

    @Override
    public APIResponseDTO<List<GetAllLoansProjection>> getByBookingId(Integer bookingId, HttpServletRequest httpRequest, String trace) {
        APIResponseDTO<List<GetAllLoansProjection>> response;
        try {
            List<GetAllLoansProjection> projections = loanRepository.getByBookingId(bookingId);
            if (Objects.isNull(projections) || projections.isEmpty()) {
                response = APIResponseDTO.<List<GetAllLoansProjection>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.SUCCESS_OPERATION_WITHOUT_RESULTS.getMessage()).build();
                return response;
            }
            log.info("Trace <{}> - Consulta de préstamos por reserva exitosa", trace);
            response = APIResponseDTO.<List<GetAllLoansProjection>>builder().status(Boolean.TRUE).message(Constants.APIMessageType.SUCCESS_OPERATION.getMessage()).data(projections).build();
        } catch (Exception e) {
            response = APIResponseDTO.<List<GetAllLoansProjection>>builder().status(Boolean.FALSE).message(Constants.APIMessageType.GENERAL_ERROR.getMessage()).build();
            log.error("Trace <{}> - Error general - ", trace, e);
        }
        return response;
    }

}
