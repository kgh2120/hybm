package com.dragontrain.md.domain.food.controller.response;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class ExpectedExpirationDate {
	private final int year;
	private final int month;
	private final int day;

	public static ExpectedExpirationDate from(LocalDate localDate) {
		return ExpectedExpirationDate.builder()
			.year(localDate.getYear())
			.month(localDate.getMonthValue())
			.day(localDate.getDayOfMonth())
			.build();
	}
}
