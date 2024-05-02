package com.dragontrain.md.domain.food.controller.request;

import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceiptEachRequest {

	private String name;
	private Integer categoryId;
	private Integer price;
	private LocalDate expiredDate;
	private StorageTypeId location;
}
