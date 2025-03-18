package com.github.rkondratowicz;

import com.github.rkondratowicz.lib.AuditEvent;
import com.github.rkondratowicz.lib.ErrorEvent;

public class EventCatalog {

    public record MsgReceived(String messageId, String result) implements AuditEvent {
    }

    public record FooEvent(String someParam) implements AuditEvent {
    }

    public record BarEvent(int number) implements AuditEvent {
    }

    public record Oopsie(String postId, Exception exc) implements ErrorEvent {
    }
}
