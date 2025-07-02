package com.troyadevclub.integraservicios.dto.feature;

import lombok.Data;

import java.util.List;

@Data
public class ExternalDataResponseDTO {

    private List<ExternalData> recursos_disponibles;

    @Data
    private static class ExternalData {

        private String id_recurso;

        private String nombre;

        private String tipo_recurso;

        private String[] horario_disponibilidad;

    }

}
