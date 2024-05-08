package com.dragontrain.md.domain.user.controller.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoginResponse {

	private final Integer year;
	private final Integer month;

	public static LoginResponse of(LocalDateTime createdAt){
		return new LoginResponse(createdAt.getYear(), createdAt.getMonthValue());
	}
}
