package com.dragontrain.md.domain.recipe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
public class RecipeFoodId implements Serializable {

	@EqualsAndHashCode.Include
	@Column(name = "recipe_id", columnDefinition = "int")
	private Integer recipeId;

	@EqualsAndHashCode.Include
	@Column(name = "category_detail_id", columnDefinition = "int")
	private Integer categoryDetailId;
}
