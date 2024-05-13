package com.dragontrain.md.domain.recipe.controller.response;

import com.dragontrain.md.domain.recipe.domain.Recipe;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeResponse {
	private Integer recipeId;
	private String title;
	private String author;

	public static RecipeResponse create(Recipe recipe) {
		return RecipeResponse.builder()
			.recipeId(recipe.getRecipeId())
			.title(recipe.getName())
			.author(recipe.getAuthor())
			.build();
	}
}
