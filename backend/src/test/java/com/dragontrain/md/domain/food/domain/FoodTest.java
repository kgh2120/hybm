package com.dragontrain.md.domain.food.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

class FoodTest {

	@Test
	void calculateFoodStatusTest() throws Exception {
		// given

		LocalDate now = LocalDate.of(2024, 5, 2);
		LocalDate yes = LocalDate.of(2024, 5, 1);
		LocalDate tom = LocalDate.of(2024, 5, 3);
		// when
		Period until = now.until(yes);
		int days = until.getDays();
		System.out.println(days);

		Period untilT = now.until(tom);
		int daysT = untilT.getDays();
		System.out.println(daysT);
		// then
	}

	@Test
	void localDateParseTest() throws Exception {
		// given

		LocalDate parse = LocalDate.parse("2024-05-02");
		// then
		assertThat(parse.getYear()).isEqualTo(2024);
		assertThat(parse.getMonthValue()).isEqualTo(5);
		assertThat(parse.getDayOfMonth()).isEqualTo(2);
	}

	@Test
	void localDateParseExceptionTest() throws Exception {
		// given
		assertThatThrownBy(() -> LocalDate.parse("2024-04-31"))
			.isInstanceOf(DateTimeParseException.class);
		assertThatThrownBy(() -> LocalDate.parse("123123"))
			.isInstanceOf(DateTimeParseException.class);

	}

}
