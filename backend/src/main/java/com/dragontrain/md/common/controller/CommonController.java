package com.dragontrain.md.common.controller;

import com.dragontrain.md.common.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/common")
@RestController
public class CommonController {
	private final FileService fileService;

	@GetMapping
	public ResponseEntity<Void> getRecipeData() {
		fileService.setData();
		return ResponseEntity.ok().build();
	}
}
