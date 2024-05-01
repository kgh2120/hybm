package com.dragontrain.md.domain.refrigerator.controller;

import com.dragontrain.md.domain.refrigerator.controller.response.StorageDesignsResponse;
import com.dragontrain.md.domain.refrigerator.service.StorageStorageDesignService;
import com.dragontrain.md.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/refrigerators")
public class RefrigeratorController {

	private final StorageStorageDesignService storageStorageDesignService;

	@GetMapping("/designs")
	public StorageDesignsResponse findAllRefrigeratorDesigns(
		@AuthenticationPrincipal User user
		) {
		return storageStorageDesignService.findAllStorageDesign(user);
	}

}
