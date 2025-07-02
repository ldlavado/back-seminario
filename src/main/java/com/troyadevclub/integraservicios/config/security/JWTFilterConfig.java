package com.troyadevclub.integraservicios.config.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.troyadevclub.integraservicios.utils.Constants;
import com.troyadevclub.integraservicios.utils.Utils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Log4j2
public class JWTFilterConfig extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            String authenticationHeader = request.getHeader(Constants.Util.HEADER);
            if (Objects.isNull(authenticationHeader) || !authenticationHeader.startsWith(Constants.Util.BEARER)) {
                throw new JWTVerificationException(Constants.Message.INVALID_AUTH);
            }
            DecodedJWT jwt = Utils.getDecodedJWT(request);
            if(Objects.nonNull(jwt.getExpiresAt()) && jwt.getExpiresAt().compareTo(new Date()) < 0) {
                throw new JWTVerificationException(Constants.Message.EXPIRED_AUTH);
            }
            String userData = jwt.getClaim("userData").asString();
            JSONObject jsonObject = new JSONObject(userData);
            List<GrantedAuthority> authority = List.of(new SimpleGrantedAuthority("ROLE_" + jsonObject.get("TIPO")));
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    jsonObject.get("USUA_CORREO"),
                    null,
                    authority);
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
            log.info("- - - - - > {} < - - - - -", Constants.Message.VALID_AUTH);
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            log.info("- - - - - > {} - {} < - - - - -", Constants.Message.FAILED_AUTH, e);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            SecurityContextHolder.clearContext();
        } catch (ConstraintViolationException e2) {
            log.info("- - - - - > {} - {} < - - - - -", "Constraint error", e2);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            SecurityContextHolder.clearContext();
        } catch (Exception eg) {
            log.info("- - - - - > {} - {} < - - - - -", "General error", eg);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            SecurityContextHolder.clearContext();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return isSafeRoute(path);
    }

    private boolean isSafeRoute(String path) {
        return path.startsWith("/swagger-ui/") || path.contains("swagger") || path.endsWith("/swagger-ui/index.html")
                || path.startsWith("/v3/api-docs") || path.contains("/actuator/") || path.startsWith("/auth")
                || path.startsWith("/external");
    }

}