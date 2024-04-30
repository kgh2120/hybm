package com.dragontrain.md.common.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface TimeService {

	LocalDateTime localDateTimeNow();

	LocalDate localDateNow();
}
