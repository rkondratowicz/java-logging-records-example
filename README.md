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

## Prerequisites

- Java 17 or later (for records support)

## Getting Started

### Running the Project

The project uses Gradle wrapper, so you don't need to have Gradle installed. Simply run:

```bash
# On Unix-like systems (Linux, macOS)
./gradlew run

# On Windows
gradlew.bat run
```

This will compile and run the example application, demonstrating the structured logging functionality.

## Project Structure

- `src/main/java/com/github/rkondratowicz/`
  - `Main.java` - Example usage
  - `EventCatalog.java` - Predefined log events
  - `lib/` - Core logging implementation
    - `EventLogger.java` - Main logging class
    - `EventLoggerFactory.java` - Logger factory
    - `EventRecordHelper.java` - Record reflection helper
    - `Event.java`, `AuditEvent.java`, `ErrorEvent.java` - Event interfaces

## Implementation Details

### Configuration

The project uses Logback with JSON layout for structured logging. The configuration is defined in `src/main/resources/logback.xml`:

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

Key configuration points:
- Uses `ConsoleAppender` to output logs to the console
- Implements JSON layout using `JsonLayout` from logback-json-classic
- Uses Jackson for JSON formatting
- Timestamps are in UTC with millisecond precision
- Root logging level is set to INFO
- Each log entry is separated by a line separator

To modify the logging behavior, you can:
- Change the root level (e.g., to DEBUG for more verbose logging)
- Add additional appenders (e.g., for file logging)
- Customize the JSON format by modifying the layout properties
- Add custom fields to the JSON output

### Example Implementation

The project demonstrates how to create type-safe log events using records:

```java
public record MessageReceived(
    String messageId,
    String result
) implements AuditEvent {
}
```

And how to use them in your code:

```java
private static final EventLogger log = EventLoggerFactory.getLogger(YourClass.class);

// Log an audit event
log.audit(new MessageReceived(messageId, result));

// Log an error event
log.error(new Oopsie("123", exception));
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
