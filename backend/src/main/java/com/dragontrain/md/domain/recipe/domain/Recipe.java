package com.dragontrain.md.domain.recipe.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
	private List<RecipeFood> recipeFoodList;
}
