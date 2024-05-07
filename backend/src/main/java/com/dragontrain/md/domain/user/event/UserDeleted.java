package com.dragontrain.md.domain.user.event;

import com.dragontrain.md.common.config.event.Event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@Getter
@RequiredArgsConstructor
public class UserDeleted implements Event {
	private final Long userId;
}

