package com.dragontrain.md.domain.refrigerator.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
public class RefrigeratorCategoryBigId implements Serializable {

	@EqualsAndHashCode.Include
	@Column(name = "category_big_id", columnDefinition = "tinyint")
	private Integer categoryBigId;
	@EqualsAndHashCode.Include
	@Column(name = "refrigerator_id", columnDefinition = "bigint")
	private Long refrigeratorId;

}
