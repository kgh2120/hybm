package com.dragontrain.md.domain.food.controller.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceiptProducts {
	private List<ReceiptProduct> receiptProducts;
}
