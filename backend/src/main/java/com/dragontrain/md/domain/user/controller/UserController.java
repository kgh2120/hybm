package com.dragontrain.md.domain.user.controller;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dragontrain.md.common.config.exception.ErrorResponse;
import com.dragontrain.md.common.config.exception.GlobalErrorCode;
import com.dragontrain.md.common.config.exception.TokenException;
import com.dragontrain.md.common.config.jwt.Token;
import com.dragontrain.md.common.config.utils.CookieUtils;
import com.dragontrain.md.domain.user.controller.response.LoginResponse;
import com.dragontrain.md.domain.user.domain.User;
import com.dragontrain.md.domain.user.service.Tokens;
import com.dragontrain.md.domain.user.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

	private final UserService userService;

	@GetMapping("/is-login")
	public ResponseEntity<LoginResponse> isLogin(@AuthenticationPrincipal User user) {
		return ResponseEntity.ok(LoginResponse.of(user.getCreatedAt()));
	}

	@GetMapping("/login-fail")
	public ResponseEntity<ErrorResponse> failLogin() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			.body(ErrorResponse.createErrorResponse(GlobalErrorCode.UNAUTHORIZED_ACCESS, "/"));
	}

	@GetMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletResponse response) {
		response.addCookie(CookieUtils.deleteAccessTokenCookie());
		response.addCookie(CookieUtils.deleteRefreshTokenCookie());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/reissue")
	public ResponseEntity<Void> reissueTokens(
		@CookieValue(value = "refresh_token", required = false) Cookie refreshTokenCookie,
		HttpServletResponse response) {

		if (Objects.isNull(refreshTokenCookie))
			throw new TokenException(GlobalErrorCode.REFRESH_TOKEN_MISSING);

		Tokens tokens = userService.reissueToken(refreshTokenCookie.getValue());

		Token accessToken = tokens.getAccessToken();
		Token refreshToken = tokens.getRefreshToken();

		response.addCookie(CookieUtils.makeAccessTokenCookie(accessToken.getValue(), accessToken.getTtlToSecond()));
		response.addCookie(CookieUtils.makeRefreshTokenCookie(refreshToken.getValue(), refreshToken.getTtlToSecond()));

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/sign-out")
	public ResponseEntity<Void> signOut(@AuthenticationPrincipal User user, HttpServletResponse response){

		userService.signOut(user);

		response.addCookie(CookieUtils.deleteAccessTokenCookie());
		response.addCookie(CookieUtils.deleteRefreshTokenCookie());
		return ResponseEntity.ok().build();
	}
}
