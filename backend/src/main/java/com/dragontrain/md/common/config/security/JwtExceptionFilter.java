package com.dragontrain.md.common.config.security;

import static com.dragontrain.md.common.config.exception.GlobalErrorCode.*;

import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dragontrain.md.common.config.exception.CustomException;
import com.dragontrain.md.common.config.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

	private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (JwtException | CustomException | CannotCreateTransactionException |
				 FileSizeLimitExceededException | SizeLimitExceededException e) {
			log.debug("error occurred! {} ", e);
			sendErrorResponse(e, request, response);
		}
	}

	private void sendErrorResponse(Throwable e, HttpServletRequest request, HttpServletResponse response) throws IOException {

		ErrorResponse errorResponse = null;
		if (e instanceof MalformedJwtException) {
			errorResponse = ErrorResponse.createErrorResponse(TOKEN_MALFORMED, request.getRequestURI());
		}
		else if (e instanceof ExpiredJwtException) {
			errorResponse = ErrorResponse.createErrorResponse(TOKEN_EXPIRED, request.getRequestURI());
		}
		else if (e instanceof UnsupportedJwtException) {
			errorResponse = ErrorResponse.createErrorResponse(TOKEN_UNSUPPORTED, request.getRequestURI());
		}
		else if (e instanceof SignatureException) {
			errorResponse = ErrorResponse.createErrorResponse(TOKEN_NOT_VERIFIED, request.getRequestURI());
		} else if (e instanceof CustomException) {
			errorResponse = ErrorResponse.createErrorResponse(((CustomException) e).getErrorCode(),
				request.getRequestURI());
		}

		sendResponse(response, errorResponse.getHttpStatus(), objectMapper.writeValueAsString(errorResponse));;
	}

	private void sendResponse(HttpServletResponse response, HttpStatus httpStatus,
		String body
	)  throws IOException{
		response.setStatus(httpStatus.value());
		response.setCharacterEncoding("utf-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().println(body);

	}

}
