package com.dragontrain.md.domain.recipe.service;

import com.dragontrain.md.domain.recipe.controller.response.RecipeResponse;
import com.dragontrain.md.domain.user.domain.User;

import java.util.List;


public interface RecipeService {

	List<RecipeResponse> getRecommend(Long[] foodIds, User user);
}
