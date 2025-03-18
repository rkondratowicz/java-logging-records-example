# Structured Logging in Java - Sample Project

A sample project demonstrating type-safe, structured logging in Java using records and MDC (Mapped Diagnostic Context).

## Overview

This project implements the logging techniques described in the blog post [Beyond structured logs](https://rafalko.dev/blog/beyond-structured-logs).
It showcases how to create a clean, maintainable logging system using modern Java features like records and MDC.

## Features Demonstrated

- Type-safe logging using Java records
- JSON-formatted logs for better machine parsing
- MDC (Mapped Diagnostic Context) integration
- Compile-time safety for log events
- Clean and maintainable logging API

## Getting Started

### Prerequisites

- Java 17 or later (for records support)

## Implementation Details

### Configuration

The project uses Logback with JSON layout for structured logging:

```xml
<configuration>
    <appender name="json" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.core.encoder.LayoutWrappingEncoder">
            <layout class="ch.qos.logback.contrib.json.classic.JsonLayout">
                <jsonFormatter class="ch.qos.logback.contrib.jackson.JacksonJsonFormatter" />
                <timestampFormat>yyyy-MM-dd'T'HH:mm:ss.SSS</timestampFormat>
                <timestampFormatTimezoneId>UTC</timestampFormatTimezoneId>
                <appendLineSeparator>true</appendLineSeparator>
            </layout>
        </encoder>
    </appender>
    <root level="INFO">
        <appender-ref ref="json"/>
    </root>
</configuration>
```

### Example Implementation

The project demonstrates how to create type-safe log events using records:

```java
public record MessageReceived(
    String messageId,
    String result
) implements InfoEvent {
}
```

And how to use them in your code:

```java
private static final EventLogger log = EventLoggerFactory.getLogger(YourClass.class);

// Log an event
log.info(new MessageReceived(messageId, result));
```

The output will look like this:
```json
{
  "timestamp": "2025-03-17T21:41:28.948",
  "level": "INFO",
  "thread": "main",
  "mdc": {
    "event.name": "MessageReceived",
    "event.properties.message_id": "a4639312-78f1-4443-84ff-d5e6017facf8",
    "event.properties.result": "REJECT",
    "event.source": "com.example.YourClass"
  },
  "logger": "EventLogger",
  "message": "MessageReceived",
  "context": "default"
}
```

## Contributing

Feel free to fork this project and experiment with the implementation.
If you have suggestions for improvements or find bugs, please open an issue or submit a Pull Request.

## Acknowledgments

- [SLF4J](https://www.slf4j.org/)
- [Logback](https://logback.qos.ch/)
- [Jackson](https://github.com/FasterXML/jackson) 
