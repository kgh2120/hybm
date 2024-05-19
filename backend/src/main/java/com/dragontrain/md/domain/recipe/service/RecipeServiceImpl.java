package com.dragontrain.md.domain.recipe.service;

import com.dragontrain.md.domain.food.domain.Food;
import com.dragontrain.md.domain.food.exception.FoodErrorCode;
import com.dragontrain.md.domain.food.exception.FoodException;
import com.dragontrain.md.domain.food.service.port.FoodRepository;
import com.dragontrain.md.domain.recipe.controller.response.RecipeResponse;
import com.dragontrain.md.domain.recipe.domain.Ingredient;
import com.dragontrain.md.domain.recipe.domain.Recipe;
import com.dragontrain.md.domain.recipe.domain.RecipeIngredient;
import com.dragontrain.md.domain.recipe.service.port.IngredientRepository;
import com.dragontrain.md.domain.recipe.service.port.RecipeIngredientRepository;
import com.dragontrain.md.domain.recipe.service.port.RecipeRepository;
import com.dragontrain.md.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeServiceImpl implements RecipeService{

	private final FoodRepository foodRepository;
	private final IngredientRepository ingredientRepository;
	private final RecipeRepository recipeRepository;
	private final RecipeIngredientRepository recipeIngredientRepository;

	@Override
	public List<RecipeResponse> getRecommend(Long[] foodIds, User user) {

		ArrayList<Ingredient> ingredients = new ArrayList<>();
		Set<Recipe> recipes = new HashSet<>();

		// foodId마다 name과 categoryDetail 가져오기
		for (Long foodId : foodIds) {
			Food tmpFood = foodRepository.findById(foodId)
				.orElseThrow(() -> new FoodException(FoodErrorCode.FOOD_NOT_FOUND));
			// ingredient에서 각 categoryDetail과 name 찾기
			boolean tmp = ingredientRepository.findByCategoryDetailId(
				tmpFood.getCategoryDetail().getCategoryDetailId()
			).isPresent();
			if (tmp) {
				Ingredient tmpIngredient = ingredientRepository.findByCategoryDetailId(
					tmpFood.getCategoryDetail().getCategoryDetailId()
				).orElseThrow(() -> new FoodException(FoodErrorCode.FOOD_NOT_FOUND));
				ingredients.add(tmpIngredient);
			} else {
				List<Ingredient> tmpIngredients =
					ingredientRepository.findByName(tmpFood.getName());
				ingredients.addAll(tmpIngredients);
			}

			ingredients.forEach(ingredient -> {
				List<RecipeIngredient> tmpRecipeIngredients =
					recipeIngredientRepository.findByIngredientId(ingredient.getIngredientId());
				tmpRecipeIngredients.forEach(recipeIngredient -> {
					recipes.add(recipeIngredient.getRecipe());
				});
			});
		}

		List<RecipeResponse> recipeResponses = new ArrayList<>();
		for (Recipe recipe : recipes) {
			recipeResponses.add(RecipeResponse.create(recipe));
		}

		return recipeResponses;
	}
}
