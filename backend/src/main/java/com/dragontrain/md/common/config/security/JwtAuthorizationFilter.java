package com.dragontrain.md.common.config.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dragontrain.md.common.config.exception.GlobalErrorCode;
import com.dragontrain.md.common.config.exception.TokenException;
import com.dragontrain.md.common.config.jwt.JwtProvider;
import com.dragontrain.md.common.config.utils.CookieUtils;
import com.dragontrain.md.domain.user.domain.User;
import com.dragontrain.md.domain.user.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;
	private final UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		Cookie accessTokenCookie = CookieUtils.findAccessTokenCookie(request.getCookies());

		if (!Objects.isNull(accessTokenCookie)) {
			String accessToken = accessTokenCookie.getValue();
			if (jwtProvider.isRefreshToken(accessToken)) {
				throw new TokenException(GlobalErrorCode.TOKEN_TYPE_MISS_MATCHED,
					"[AUTHORIZATION] refresh token is used ");
			}
			setAuthenticated(accessToken);
		}

		filterChain.doFilter(request, response);
	}

	private void setAuthenticated(String accessToken) {
		Long userId = jwtProvider.parseUserId(accessToken);
		User user = userService.loadUserByUserId(userId);
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getUserId(), new ArrayList<>()));
	}
}
