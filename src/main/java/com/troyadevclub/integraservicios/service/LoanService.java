package com.troyadevclub.integraservicios.service;

import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.dto.loan.ModifyLoanRequestDTO;
import com.troyadevclub.integraservicios.dto.loan.GetAllLoansProjection;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface LoanService {

    APIResponseDTO<Object> createLoan(ModifyLoanRequestDTO request, String bookingId, HttpServletRequest httpRequest, String trace);

    APIResponseDTO<Object> returnLoan(ModifyLoanRequestDTO request, String loanId, String score, HttpServletRequest httpRequest, String trace);

    APIResponseDTO<List<GetAllLoansProjection>> getAll(HttpServletRequest httpRequest, String trace);

    APIResponseDTO<List<GetAllLoansProjection>> getByBookingId(Integer bookingId, HttpServletRequest httpRequest, String trace);

}
