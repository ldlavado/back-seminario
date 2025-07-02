package com.troyadevclub.integraservicios.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.troyadevclub.integraservicios.utils.Constants;
import com.troyadevclub.integraservicios.utils.Utils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class JWTFilterConfigTest {

    @InjectMocks
    private JWTFilterConfig jwtFilterConfig;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private DecodedJWT decodedJWT;

    @Autowired
    private Environment env;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternal_ValidJWT() throws IOException, ServletException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 3600);
        JSONObject userData = new JSONObject();
        userData.put("USUA_CORREO", "test@email.com");
        userData.put("TIPO", "test");
        String testToken = JWT.create()
                .withClaim("userData", userData.toString())
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC512(Base64.getDecoder().decode(Objects.requireNonNull(env.getProperty("api_auth_secret")))));

        when(request.getHeader(Constants.Util.HEADER)).thenReturn(Constants.Util.BEARER + testToken);
        when(decodedJWT.getExpiresAt()).thenReturn(new Date(System.currentTimeMillis() + 10000));

        jwtFilterConfig.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_ExpiredJWT() throws IOException, ServletException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, -3600);
        JSONObject userData = new JSONObject();
        userData.put("USUA_CORREO", "test@email.com");
        userData.put("TIPO", "test");
        String testToken = JWT.create()
                .withClaim("userData", userData.toString())
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC512(Base64.getDecoder().decode(Objects.requireNonNull(env.getProperty("api_auth_secret")))));

        when(request.getHeader(Constants.Util.HEADER)).thenReturn(Constants.Util.BEARER + testToken);
        try (MockedStatic<Utils> utils = mockStatic(Utils.class)) {
            utils.when(() -> Utils.getDecodedJWT(request)).thenReturn(decodedJWT);
        }

        jwtFilterConfig.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(response, times(1)).setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void testDoFilterInternal_MissingJWT() throws IOException, ServletException {
        when(request.getHeader(Constants.Util.HEADER)).thenReturn(null);

        jwtFilterConfig.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(response, times(1)).setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void testDoFilterInternal_InvalidJWT() throws IOException, ServletException {
        when(request.getHeader(Constants.Util.HEADER)).thenReturn(Constants.Util.BEARER + "invalid_token");
        try (MockedStatic<Utils> utils = mockStatic(Utils.class)) {
            utils.when(() -> Utils.getDecodedJWT(request)).thenThrow(new JWTDecodeException("Invalid token"));
        }

        jwtFilterConfig.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        assertThrowsExactly(JWTDecodeException.class, () -> Utils.getDecodedJWT(request));
        verify(response, times(1)).setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void testShouldNotFilter_SafeRoute() {
        when(request.getServletPath()).thenReturn("/swagger-ui/index.html");

        assertTrue(jwtFilterConfig.shouldNotFilter(request));
    }

    @Test
    void testShouldNotFilter_UnsafeRoute() {
        when(request.getServletPath()).thenReturn("/resource/data");

        assertFalse(jwtFilterConfig.shouldNotFilter(request));
    }
}