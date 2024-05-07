package com.dragontrain.md.domain.refrigerator.service;

import com.dragontrain.md.domain.refrigerator.controller.request.BadgeRequest;
import com.dragontrain.md.domain.refrigerator.controller.response.AttachedBadgeResponse;
import com.dragontrain.md.domain.refrigerator.controller.response.BadgeResponse;
import com.dragontrain.md.domain.user.domain.User;

import java.util.List;

public interface RefrigeratorService {
	void createInitialRefrigerator(Long userId);

	void deleteRefrigerator(Long userId);
	BadgeResponse getBadges(User user);
	void switchBadges(List<BadgeRequest> badgeRequests, User user);
	List<AttachedBadgeResponse> getAttachedBadges(User user);
}
