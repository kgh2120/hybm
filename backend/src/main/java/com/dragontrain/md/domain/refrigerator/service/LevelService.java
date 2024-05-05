package com.dragontrain.md.domain.refrigerator.service;

import com.dragontrain.md.domain.refrigerator.controller.response.MyLevelResponse;
import com.dragontrain.md.domain.user.domain.User;

public interface LevelService {
	MyLevelResponse getMyLevel(User user);
}
