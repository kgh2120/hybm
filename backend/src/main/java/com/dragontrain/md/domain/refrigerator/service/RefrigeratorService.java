package com.dragontrain.md.domain.refrigerator.service;

import com.dragontrain.md.domain.refrigerator.controller.response.BadgeResponse;
import com.dragontrain.md.domain.user.domain.User;

public interface RefrigeratorService {
	void createInitialRefrigerator(Long userId);

	void deleteRefrigerator(Long userId);
	BadgeResponse getBadges(User suer);
}
