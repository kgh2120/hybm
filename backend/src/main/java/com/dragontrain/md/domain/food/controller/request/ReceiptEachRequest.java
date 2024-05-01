package com.dragontrain.md.domain.food.controller.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceiptEachRequest {

	private String name;
	private String categoryId;
	private String price;
	private String expiredDate;
	private String location;
}

