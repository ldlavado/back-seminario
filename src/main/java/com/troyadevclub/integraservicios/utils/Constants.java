package com.troyadevclub.integraservicios.utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public class Constants {

    public static final class Path {

        public static final String AUTH_PATH = "auth";
        public static final String USER_PATH = "user";
        public static final String LOGIN_PATH = "login";
        public static final String REGISTER_PATH = "register";

        public static final String RESOURCE_PATH = "resource";
        public static final String RESOURCE_TYPE_SCHEDULE_PATH = "resource-type";
        public static final String SCHEDULE_PATH = "schedule";
        public static final String UNIT_SCHEDULE_PATH = "unit";
        public static final String FEATURE_PATH = "feature";
        public static final String BOOKING_PATH = "booking";
        public static final String LOAN_PATH = "loan";

        public static final String CREATE_PATH = "create";
        public static final String UPDATE_PATH = "update";
        public static final String RETURN_PATH = "return";
        public static final String UNLOCK_PATH = "unlock";
        public static final String ALL_PATH = "all";
        public static final String RESOURCE_TYPE_PATH = "type";
        public static final String ASSOCIATION_PATH = "association";

        public static final String EXTERNAL_PATH = "external";

    }

    public static class Operation {

        public static final String REGISTER_USER = "REGISTRO DE USUARIO";
        public static final String LOGIN_USER = "LOGIN DE USUARIO";
        public static final String GET_USERS = "CONSULTA DE USUARIOS";
        public static final String UNLOCK_USER = "DESBLOQUEO DE USUARIO";
        public static final String UPDATE_USER = "ACTUALIZACIÓN DE USUARIO";

        public static final String CREATE_RESOURCE = "CREACIÓN DE RECURSO";
        public static final String GET_ALL_RESOURCES = "CONSULTA GENERAL DE RECURSOS";
        public static final String GET_RESOURCE_BY_TYPE = "CONSULTA DE RECURSOS POR TIPO";
        public static final String ASSOCIATE_RESOURCE = "ASOCIACIÓN DE RECURSO";

        public static final String CREATE_RESOURCE_TYPE = "CREACIÓN DE TIPO DE RECURSO";
        public static final String GET_ALL_RESOURCE_TYPES = "CONSULTA GENERAL DE TIPOS DE RECURSOS";
        public static final String GET_RESOURCE_TYPE_BY_UNIT = "CONSULTA DE TIPOS DE RECURSOS POR UNIDAD";
        public static final String ASSOCIATE_RESOURCE_TYPE = "ASOCIACIÓN DE TIPO DE RECURSO";

        public static final String CREATE_FEATURE = "CREACIÓN DE CARACTERÍSTICA";
        public static final String GET_ALL_FEATURES = "CONSULTA GENERAL DE CARACTERÍSTICAS";
        public static final String ASSOCIATE_FEATURE = "ASOCIACIÓN DE CARACTERÍSTICA";
        public static final String GET_FEATURE_ASSOCIATIONS = "CONSULTA DE ASOCIACIONES DE CARACTERÍSTICAS";

        public static final String CREATE_BOOKING = "CREACIÓN DE RESERVA";
        public static final String GET_ALL_BOOKINGS = "CONSULTA GENERAL DE RESERVAS";
        public static final String GET_BOOKING_BY_ID = "CONSULTA DE RESERVA POR ID";
        public static final String GET_BOOKING_BY_USER_ID = "CONSULTA DE RESERVA POR ID DE USUARIO";

        public static final String CREATE_LOAN = "CREACIÓN DE PRÉSTAMO";
        public static final String RETURN_LOAN = "DEVOLUCIÓN DE PRÉSTAMO";
        public static final String GET_ALL_LOANS = "CONSULTA GENERAL DE PRÉSTAMOS";
        public static final String GET_LOANS_BY_BOOKING = "CONSULTA PRÉSTAMOS POR RESERVA";

        public static final String GET_UNIT_SCHEDULE = "CONSULTA DE HORARIOS DE UNIDAD";
        public static final String EDIT_UNIT_SCHEDULE = "EDICIÓN DE HORARIOS DE UNIDAD";
        public static final String GET_RESOURCE_TYPE_SCHEDULE = "CONSULTA DE HORARIOS DE TIPO DE RECURSO";
        public static final String EDIT_RESOURCE_TYPE_SCHEDULE = "EDICIÓN DE HORARIOS DE TIPO DE RECURSO";

        public static final String EXTERNAL_GET_DATA = "CONSULTA EXTERNA DE DATOS";

    }

    public static class Message {

        public static final String INVALID_AUTH = "Invalid authentication";
        public static final String EXPIRED_AUTH = "Expired authentication";
        public static final String FAILED_AUTH = "Failed authentication";
        public static final String VALID_AUTH = "Successful authentication";

        public static String START_SERVICE = "COMIENZA SERVICIO";
        public static String END_SERVICE = "FINALIZA SERVICIO";
        public static String REQUEST_ERROR = "ERROR EN DATOS DE REQUEST";

    }

    public static class Util {

        public static final String HEADER = "Authorization";
        public static final String BEARER = "Bearer ";

    }

    @Getter
    public enum APIMessageType {

        INVALID_REQUEST("Datos de request inválidos", BAD_REQUEST),
        SUCCESS_OPERATION("Operación exitosa", OK),
        SUCCESS_OPERATION_WITHOUT_RESULTS("Operación exitosa sin resultados", OK),
        FAILED_OPERATION("Operación fallida", BAD_REQUEST),
        GENERAL_ERROR("Error general", INTERNAL_SERVER_ERROR),
        FORBIDDEN("Acceso restringido", HttpStatus.FORBIDDEN),
        SUCCESS_REGISTER("Registro exitoso", CREATED),
        FAILED_REGISTER("Registro fallido", BAD_REQUEST),
        SUCCESS_UPDATE("Actualización exitosa", OK),
        SUCCESS_UPDATE_WITH_ERRORS("Actualización realizada con errores", OK),
        FAILED_UPDATE("Actualización fallida", BAD_REQUEST);

        APIMessageType(String message, HttpStatus code) {
            this.message = message;
            this.code = code;
        }

        private final String message;

        private final HttpStatus code;

    }

}
