package com.dragontrain.md.domain.recipe.domain;

import com.dragontrain.md.domain.food.domain.CategoryDetail;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_recipe_food")
public class RecipeFood {

	@EmbeddedId
	private RecipeFoodId recipeFoodId;

	@Column(name = "ingredient_name", columnDefinition = "varchar(100)", nullable = false)
	private String ingredientName;

	@MapsId("recipeId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Recipe recipe;

	@MapsId("categoryDetailId")
	@ManyToOne(fetch = FetchType.LAZY)
	private CategoryDetail categoryDetail;
}
