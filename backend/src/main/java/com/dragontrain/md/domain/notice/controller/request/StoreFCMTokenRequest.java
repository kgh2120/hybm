package com.dragontrain.md.domain.notice.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class StoreFCMTokenRequest {
	@NotBlank(message = "토큰을 적재해주세요.")
	private String token;
}
