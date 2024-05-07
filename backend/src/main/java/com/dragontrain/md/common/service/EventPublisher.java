package com.dragontrain.md.common.service;

import com.dragontrain.md.common.config.event.Event;

public interface EventPublisher {

	void publish(Event event);
}
