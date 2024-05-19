package com.dragontrain.md.domain.user.event;

import com.dragontrain.md.common.config.event.Event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCreated implements Event {

	private final Long userId;

}
