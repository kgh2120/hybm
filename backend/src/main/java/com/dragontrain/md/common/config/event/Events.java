package com.dragontrain.md.common.config.event;

import org.springframework.context.ApplicationEventPublisher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Events {

    private static ApplicationEventPublisher eventPublisher;

    private Events(){
        throw new IllegalStateException("do not initialize this class");
    }

    static void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        Events.eventPublisher = eventPublisher;
        log.debug("setting publisher!");
    }

    public static void raise(Event event) {
        if(eventPublisher != null)
            eventPublisher.publishEvent(event);
    }
}
