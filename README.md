# slf4json

A library to log in json using slf4j.

# Usage

### logback

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xml>
<configuration scan="true">
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>/tmp/app.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>/tmp/app_%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>5MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <maxHistory>90</maxHistory>
        </rollingPolicy>
        <encoder>
            <charset>UTF-8</charset>
            <pattern>%msg%n%n</pattern>
        </encoder>
    </appender>
    <logger name="com.rathna.app.myjson" level="TRACE" additivity="false">
    	<appender-ref ref="FILE"/>
    </logger>
    <root level="WARN">
        <appender-ref ref="CONSOLE" />
    </root>
</configuration>

```

### java

```java

import com.rathna.app.util.log.json.Logger;
import com.rathna.app.util.log.json.LoggerFactory;
..
private static final Logger LOGGER = LoggerFactory.getLogger("com.rathna.app.myjson", Integer.MAX_VALUE);


// trace log a single key-value
LOGGER.trace().field("key4", "Value4").log;

// debug log a simple message
LOGGER.debug().message("SimpleMessage").log;

// info log a list
LOGGER.info().list("Key", Lists.newArrayList("Value1", "Value2")).log;

// warn log a set of key-values
LOGGER.warn().fields(Maps.newHashMap(ImmutableMap.of("key2", "value2", "key1", "value1"))).log();

//error log an Exception
LOGGER.error().exception(new NullPointerException("My dear NPE")).log;

//error log an Exception (with trace)
LOGGER.error().exception(new NullPointerException("My dear NPE")).stack().log;

trace log combined
LOGGER.trace().field("key4", "Value4").message("SimpleMessage")
				.list("Key", Lists.newArrayList("Value1", "Value2"))
				.fields(Maps.newHashMap(ImmutableMap.of("key2", "value2", "key1", "value1")))
				.exception(new NullPointerException("My dear NPE")).stack().log();
```

# Depends on
- jackson
- slf4j
- commons-lang3
- mockito
- hamcrest
- junit-jupiter
- logback
