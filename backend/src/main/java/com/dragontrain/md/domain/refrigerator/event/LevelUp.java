package com.dragontrain.md.domain.refrigerator.event;

import com.dragontrain.md.common.config.event.Event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LevelUp implements Event {

	private Long userId;
	private Integer level;
}
