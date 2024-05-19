package com.dragontrain.md.domain.food.event;

import com.dragontrain.md.common.config.event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EatenCountRaised implements Event {
	private final Long userId;
	private final Long foodId;
}
