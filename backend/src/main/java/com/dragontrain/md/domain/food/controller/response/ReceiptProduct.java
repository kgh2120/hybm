package com.dragontrain.md.domain.food.controller.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceiptProduct {
	private String name;
	private String cost;
}
