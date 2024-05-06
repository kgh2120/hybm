package com.dragontrain.md.domain.refrigerator.controller;

import com.dragontrain.md.domain.refrigerator.controller.response.BadgeResponse;
import com.dragontrain.md.domain.refrigerator.service.RefrigeratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dragontrain.md.domain.refrigerator.controller.request.ModifyAppliedStorageDesignRequest;
import com.dragontrain.md.domain.refrigerator.controller.response.AppliedStorageDesignsResponse;
import com.dragontrain.md.domain.refrigerator.controller.response.StorageDesignsResponse;
import com.dragontrain.md.domain.refrigerator.service.StorageStorageDesignService;
import com.dragontrain.md.domain.user.domain.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/refrigerators")
public class RefrigeratorController {

	private final StorageStorageDesignService storageStorageDesignService;
	private final RefrigeratorService refrigeratorService;

	@GetMapping("/designs")
	public ResponseEntity<StorageDesignsResponse> findAllRefrigeratorDesigns(
		@AuthenticationPrincipal User user
	) {
		return ResponseEntity.ok(storageStorageDesignService.findAllStorageDesign(user));
	}

	@GetMapping("/designs/using")
	public ResponseEntity<AppliedStorageDesignsResponse> findAllAppliedStorageDesigns(
		@AuthenticationPrincipal User user
	) {
		return ResponseEntity.ok(storageStorageDesignService.findAllAppliedStorageDesign(user));
	}

	@PutMapping("/designs")
	public ResponseEntity<Void> modifyAppliedStorageDesign(
		@AuthenticationPrincipal User user,
		@RequestBody ModifyAppliedStorageDesignRequest request
	) {
		storageStorageDesignService.modifyAppliedStorageDesign(user, request);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/badges")
	public ResponseEntity<BadgeResponse> getBadges(@AuthenticationPrincipal User user) {

		return ResponseEntity.ok(refrigeratorService.getBadges(user));
	}

}
