package com.dragontrain.md.domain.food.controller.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceiptRequest {

	private List<ReceiptEachRequest> receiptEachRequests;
}
