package com.github.rkondratowicz.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;
import java.util.function.Consumer;

public class EventLogger {

    private static final Logger log = LoggerFactory.getLogger("EventLogger");

    private final String eventSource;
    private final EventRecordHelper helper;

    public EventLogger(String eventSource, EventRecordHelper helper) {
        this.eventSource = eventSource;
        this.helper = helper;
    }

    public void audit(AuditEvent event) {
        log(event, eventName -> log.info(eventName));
    }

    public void error(ErrorEvent event) {
        log(event, eventName -> log.error(eventName, event.exc()));
    }

    private void log(Event event, Consumer<String> logger) {
        var eventName = helper.getName(event.getClass());
        var fields = helper.getFieldsAsMap(event);

        try {
            MDC.put("event.name", eventName);
            MDC.put("event.source", this.eventSource);
            if (fields != null) {
                fields.forEach((k, v) -> MDC.put("event.properties." + k, v));
            }
            logger.accept(eventName);
        } finally {
            cleanUpMDC(fields);
        }
    }

    private void cleanUpMDC(Map<String, String> params) {
        MDC.remove("event.name");
        MDC.remove("event.source");
        if (params != null) {
            params.forEach((k, v) -> MDC.remove("event.properties." + k));
        }
    }
}
