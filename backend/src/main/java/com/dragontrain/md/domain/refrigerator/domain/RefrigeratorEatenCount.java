package com.dragontrain.md.domain.refrigerator.domain;

import com.dragontrain.md.domain.food.domain.CategoryBig;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_refrigerator_eaten_count")
public class RefrigeratorEatenCount {

	@EmbeddedId
	private RefrigeratorCategoryBigId refrigeratorCategoryBigId;

	@Column(name = "eaten_count", columnDefinition = "tinyint")
	private Integer eatenCount;

	@MapsId("categoryBigId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_big_id", columnDefinition = "tinyint")
	private CategoryBig categoryBig;

	@MapsId("refrigeratorId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "refrigerator_id",  columnDefinition = "bigint")
	private Refrigerator refrigerator;

	public static RefrigeratorEatenCount create(Refrigerator refrigerator, CategoryBig categoryBig) {
		return RefrigeratorEatenCount.builder()
			.refrigeratorCategoryBigId(RefrigeratorCategoryBigId.builder()
				.refrigeratorId(refrigerator.getRefrigeratorId())
				.categoryBigId(categoryBig.getCategoryBigId())
				.build())
			.eatenCount(0)
			.categoryBig(categoryBig)
			.refrigerator(refrigerator)
			.build();

	}

	public void eaten() {
		this.eatenCount++;
	}
}
