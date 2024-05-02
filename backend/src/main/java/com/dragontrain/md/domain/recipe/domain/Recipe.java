package com.dragontrain.md.domain.recipe.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_recipe")
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recipe_id", columnDefinition = "int", nullable = false)
	private Integer recipeId;
	@Column(name = "name", columnDefinition = "varchar(64)", nullable = false)
	private String name;

	@Column(name = "author", columnDefinition = "varchar(20)")
	private String author;

	@OneToMany(mappedBy = "recipe")
	private List<RecipeFood> recipeFoodList = new ArrayList<>();
}
