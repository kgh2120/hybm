package com.dragontrain.md.domain.food.controller.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceiptProductResponse {
	private String name;
	private String cost;
}
