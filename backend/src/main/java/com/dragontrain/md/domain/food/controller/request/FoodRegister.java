package com.dragontrain.md.domain.food.controller.request;

import com.dragontrain.md.common.config.constraint.EnumType;
import com.dragontrain.md.domain.refrigerator.domain.StorageTypeId;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FoodRegister {

	@NotBlank(message = "음식의 이름은 필수적으로 입력해야 합니다.")
	@Size(max = 100)
	private String name;
	@NotNull(message = "소분류 아이디를 넣어주세요.")
	@Min(value = 1, message = "소분류 아이디는 양수를 넣어주세요.")
	private Integer categoryId;
	private Integer price;
	@NotBlank(message = "예상 소비기한을 입력해주세요.")
	private String expiredDate;
	@NotNull(message = "넣을 위치를 입력해주세요.")
	@EnumType(targetEnum = StorageTypeId.class, message = "ice, cool, cabinet에 해당하는 값을 입력해주세요.")
	private String location;
	@NotNull(message = "수동 여부를 전달해주세요.")
	private Boolean isManual;
}
