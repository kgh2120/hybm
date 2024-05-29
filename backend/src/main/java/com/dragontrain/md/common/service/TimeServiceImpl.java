package com.dragontrain.md.common.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class TimeServiceImpl implements TimeService {
	@Override
	public LocalDateTime localDateTimeNow() {
		return LocalDateTime.now();
	}

	@Override
	public LocalDate localDateNow() {
		return LocalDate.now();
	}
}
