package com.dragontrain.md.domain.refrigerator.domain;

import com.dragontrain.md.domain.food.domain.CategoryBig;
import jakarta.persistence.*;
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
@Table(name = "tbl_badge")
public class Badge {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "badge_id", columnDefinition = "smallint", nullable = false)
	private Integer badgeId;

	@Column(name = "badge_name", columnDefinition = "varchar(100)", nullable = false)
	private String badgeName;

	@Column(name = "img_src", columnDefinition = "varchar(255)", nullable = false)
	private String imgSrc;

	@Column(name = "badge_require", columnDefinition = "varchar(255)", nullable = false)
	private String badgeRequire;

	@JoinColumn(name = "category_big_id")
	@OneToOne(fetch = FetchType.LAZY)
	private CategoryBig categoryBig;
}
