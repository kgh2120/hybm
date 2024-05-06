package com.dragontrain.md.domain.refrigerator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dragontrain.md.domain.refrigerator.controller.response.MyLevelResponse;
import com.dragontrain.md.domain.refrigerator.service.LevelService;
import com.dragontrain.md.domain.user.domain.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class LevelController {
	private final LevelService levelService;

	@GetMapping("/api/levels")
	public ResponseEntity<MyLevelResponse> getMyLevel(@AuthenticationPrincipal User user) {
		return ResponseEntity.ok(levelService.getMyLevel(user));
	}

}
