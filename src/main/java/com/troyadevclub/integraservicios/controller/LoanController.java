package com.troyadevclub.integraservicios.controller;

import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.dto.loan.ModifyLoanRequestDTO;
import com.troyadevclub.integraservicios.dto.loan.GetAllLoansProjection;
import com.troyadevclub.integraservicios.service.LoanService;
import com.troyadevclub.integraservicios.utils.Constants;
import com.troyadevclub.integraservicios.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping(value = Constants.Path.LOAN_PATH)
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping(path = Constants.Path.CREATE_PATH, consumes = { "application/json" }, produces = {"application/json; charset=utf-8" })
    public ResponseEntity<APIResponseDTO<Object>> createLoan(@Valid @RequestBody ModifyLoanRequestDTO request, BindingResult result, @RequestParam @Size(min = 1, max = 999) String bookingId, HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.CREATE_LOAN;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<Object> response;
        if(result.hasErrors()) {
            log.error(Constants.Message.REQUEST_ERROR);
            List<String> errors = Utils.getRequestFieldErrors(result);
            response = APIResponseDTO.builder().status(Boolean.FALSE).message(String.format("%1$s - %2$s", Constants.APIMessageType.INVALID_REQUEST.getMessage(), errors)).build();
            log.error("Errores del request: {}", Utils.objectToJson(errors));
            log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
            return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
        }
        response = loanService.createLoan(request, bookingId, httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

    @PostMapping(path = Constants.Path.RETURN_PATH, consumes = { "application/json" }, produces = {"application/json; charset=utf-8" })
    public ResponseEntity<APIResponseDTO<Object>> returnLoan(@Valid @RequestBody ModifyLoanRequestDTO request, BindingResult result, @RequestParam @Size(min = 1, max = 999) String loanId, @RequestParam @Range(min = 1, max = 5) Integer score, HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.RETURN_LOAN;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<Object> response;
        if(result.hasErrors()) {
            log.error(Constants.Message.REQUEST_ERROR);
            List<String> errors = Utils.getRequestFieldErrors(result);
            response = APIResponseDTO.builder().status(Boolean.FALSE).message(String.format("%1$s - %2$s", Constants.APIMessageType.INVALID_REQUEST.getMessage(), errors)).build();
            log.error("Errores del request: {}", Utils.objectToJson(errors));
            log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
            return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
        }
        response = loanService.returnLoan(request, loanId, String.valueOf(score), httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

    @GetMapping(path = Constants.Path.ALL_PATH, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<APIResponseDTO<List<GetAllLoansProjection>>> getAllLoans(HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.GET_ALL_LOANS;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<List<GetAllLoansProjection>> response = loanService.getAll(httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

    @GetMapping(path = Constants.Path.BOOKING_PATH, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<APIResponseDTO<List<GetAllLoansProjection>>> getByBookingId(@RequestParam @Range(min = 1, max = 999) Integer id, HttpServletRequest httpRequest){
        String trace = UUID.randomUUID().toString();
        String operationName = Constants.Operation.GET_LOANS_BY_BOOKING;
        log.info("{} - {} - Trace <{}>", Constants.Message.START_SERVICE, operationName, trace);
        APIResponseDTO<List<GetAllLoansProjection>> response = loanService.getByBookingId(id, httpRequest, trace);
        log.info("{} - {} - Trace <{}>", Constants.Message.END_SERVICE, operationName, trace);
        return new ResponseEntity<>(response, Utils.findCodeByMessage(response));
    }

}
