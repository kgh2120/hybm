package com.dragontrain.md.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dragontrain.md.common.config.exception.ErrorResponse;
import com.dragontrain.md.common.config.exception.GlobalErrorCode;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/api/users")
@RestController
public class UserController {

	@GetMapping("/is-login")
	public ResponseEntity<Void> isLogin(){
		return ResponseEntity.ok().build();
	}
	@GetMapping("/login-fail")
	public ResponseEntity<ErrorResponse> failLogin(HttpServletRequest request){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			.body(ErrorResponse.createErrorResponse(GlobalErrorCode.UNAUTHORIZED_ACCESS, "/"));
	}
}
