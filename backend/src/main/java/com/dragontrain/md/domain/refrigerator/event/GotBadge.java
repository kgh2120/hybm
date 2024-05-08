package com.dragontrain.md.domain.refrigerator.event;

import com.dragontrain.md.common.config.event.Event;
import com.dragontrain.md.domain.food.domain.CategoryBig;
import com.dragontrain.md.domain.refrigerator.domain.Refrigerator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GotBadge implements Event {
	private final Refrigerator refrigerator;
	private final CategoryBig categoryBig;
}
