package com.dragontrain.md.domain.recipe.controller;

import com.dragontrain.md.domain.recipe.controller.response.RecipeResponse;
import com.dragontrain.md.domain.recipe.service.RecipeService;
import com.dragontrain.md.domain.user.domain.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
@RestController
public class RecipeController {
	private final RecipeService recipeService;

	@GetMapping("/recommend")
	public ResponseEntity<List<RecipeResponse>> getRecommend(
		@RequestParam @NotNull @NotEmpty(message = "foodId를 1개 이상 보내야 합니다.") Long[] foodId,
		@AuthenticationPrincipal User user) {

		return ResponseEntity.ok(recipeService.getRecommend(foodId, user));
	}
}
