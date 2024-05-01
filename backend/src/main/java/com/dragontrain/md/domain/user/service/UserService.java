package com.dragontrain.md.domain.user.service;

import com.dragontrain.md.domain.user.domain.User;

public interface UserService {

	User loadUserByUserId(Long userId);
}
