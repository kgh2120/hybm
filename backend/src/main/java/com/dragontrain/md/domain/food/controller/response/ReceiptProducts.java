package com.dragontrain.md.domain.food.controller.response;

import java.util.List;

import org.springframework.web.context.annotation.RequestScope;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceiptProducts {
	private List<ReceiptProduct> receiptProducts;
}
