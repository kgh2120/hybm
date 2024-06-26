package com.dragontrain.md.common.config.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.dragontrain.md.common.config.jwt.JwtProvider;
import com.dragontrain.md.domain.user.oauth.CustomOAuth2Service;
import com.dragontrain.md.domain.user.oauth.OAuth2FailureHandler;
import com.dragontrain.md.domain.user.oauth.OAuth2SuccessHandler;
import com.dragontrain.md.domain.user.oauth.RedisAuthorizationRequestRepository;
import com.dragontrain.md.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
@EnableScheduling
public class WebSecurityConfig {

	private final CustomOAuth2Service customOAuth2Service;
	private final OAuth2SuccessHandler oAuth2SuccessHandler;
	private final OAuth2FailureHandler oAuth2FailureHandler;
	private final RedisAuthorizationRequestRepository redisAuthorizationRequestRepository;
	private final UserService userService;
	private final ObjectMapper objectMapper;
	private final JwtProvider jwtProvider;

	@Value("${secret.redirect.login-fail}")
	private String loginFailRedirectUrl;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.headers(headersConfigurer -> headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.formLogin(FormLoginConfigurer::disable)
			.cors(c -> c.configurationSource(corsConfigurationSource()))
			.authorizeHttpRequests((auth) ->
				auth.requestMatchers("/h2-console/**", "/oauth2/**", "/login/**",
						"/api/users/login-fail", "/api/users/reissue", "/actuator/**").permitAll()
//					.anyRequest().permitAll()
					.anyRequest().authenticated()
			)
			.oauth2Login(config ->
				config.userInfoEndpoint(c -> c.userService(customOAuth2Service))
					.authorizationEndpoint(c -> c.authorizationRequestRepository(redisAuthorizationRequestRepository))
					.successHandler(oAuth2SuccessHandler)
					.failureHandler(oAuth2FailureHandler)
					.loginPage(loginFailRedirectUrl)
			)
			.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(jwtExceptionFilter(), JwtAuthorizationFilter.class)
		;
		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOriginPatterns(List.of("*"));
		configuration.setAllowedMethods(List.of("POST", "GET", "DELETE", "PUT", "PATCH"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setExposedHeaders(List.of("*"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() {
		return new JwtAuthorizationFilter(jwtProvider, userService);
	}

	@Bean
	public JwtExceptionFilter jwtExceptionFilter() {
		return new JwtExceptionFilter(objectMapper);
	}

}
