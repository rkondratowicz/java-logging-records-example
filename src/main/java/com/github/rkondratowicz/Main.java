package com.github.rkondratowicz;

import com.github.rkondratowicz.EventCatalog.FooEvent;
import com.github.rkondratowicz.EventCatalog.Oopsie;
import com.github.rkondratowicz.lib.EventLogger;
import com.github.rkondratowicz.lib.EventLoggerFactory;

public class Main {

    private static final EventLogger log = EventLoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        log.audit(new FooEvent("hello"));

        try {
            int a = 10;
            int b = 0;
            int x = a / b;
        } catch (Exception exc) {
            log.error(new Oopsie("123", exc));
        }
    }
}
