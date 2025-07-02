package com.troyadevclub.integraservicios.dto.booking;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class CreateBookingRequestDTO implements Serializable {

    @NotEmpty
    @Size(min = 1, max = 5)
    private List<RequestData> resourceIds;

    @NotEmpty
    @Size(min = 1, max = 3)
    private String unitId;

    @NotEmpty
    @Pattern(regexp = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$", message = "Hora de comienzo inválida")
    private String startDate;

    @NotEmpty
    @Pattern(regexp = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$", message = "Hora de finalización inválida")
    private String endDate;

    @Serial
    private static final long serialVersionUID = 8660548399823480891L;

    @Override
    public String toString() {
        return resourceIds.stream()
                .map(RequestData::toString)
                .reduce("[", (partialString, element) -> partialString.equals("[") ? partialString + element : partialString + ", " + element) + "]";
    }

    @Data
    @Builder
    public static class RequestData {

        private String id;

        private String q;

        @Override
        public String toString() {
            return "{\"id\":\"" + id + "\", \"q\":" + q + "}";
        }
    }

}
