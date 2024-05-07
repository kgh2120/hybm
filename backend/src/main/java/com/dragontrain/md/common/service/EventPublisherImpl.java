package com.dragontrain.md.common.service;

import org.springframework.stereotype.Component;

import com.dragontrain.md.common.config.event.Event;
import com.dragontrain.md.common.config.event.Events;

@Component
public class EventPublisherImpl implements EventPublisher {
	@Override
	public void publish(Event event) {
		Events.raise(event);
	}
}
