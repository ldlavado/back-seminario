package com.troyadevclub.integraservicios.service;

import com.troyadevclub.integraservicios.dto.APIResponseDTO;
import com.troyadevclub.integraservicios.dto.booking.CreateBookingRequestDTO;
import com.troyadevclub.integraservicios.dto.booking.GetAllBookingsProjection;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface BookingService {

    APIResponseDTO<Object> create(CreateBookingRequestDTO request, HttpServletRequest httpRequest, String trace);

    APIResponseDTO<List<GetAllBookingsProjection>> getAll(HttpServletRequest httpRequest, String trace);

    APIResponseDTO<GetAllBookingsProjection> getById(Integer bookingId, HttpServletRequest httpRequest, String trace);

    APIResponseDTO<List<GetAllBookingsProjection>> getByUserId(HttpServletRequest httpRequest, String trace);

}
