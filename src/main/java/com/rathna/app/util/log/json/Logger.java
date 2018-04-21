package com.rathna.app.util.log.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Logger {
	public static final String TRACE = "TRACE";
	public static final String DEBUG = "DEBUG";
	public static final String INFO = "INFO";
	public static final String WARN = "WARN";
	public static final String ERROR = "ERROR";
	private org.slf4j.Logger sLogger;
	private ObjectMapper json = new ObjectMapper();
	private NoopLogger noopLogger = new NoopLogger();
	int maxLogLength = Integer.MAX_VALUE;

	public Logger(org.slf4j.Logger sLogger, int maxLogLength) {
		this.sLogger = sLogger;
		this.json.setSerializationInclusion(Include.NON_NULL);
		this.maxLogLength = maxLogLength;
	}
	public JsonLogger trace() {
		if (sLogger.isTraceEnabled()) {
			return new StdJsonLogger(json, TRACE, this.sLogger::trace, maxLogLength, this.sLogger::trace);
		} else {
			return noopLogger;
		}
	}
	public JsonLogger debug() {
		if (sLogger.isDebugEnabled()) {
			return new StdJsonLogger(json, DEBUG, this.sLogger::debug, maxLogLength, this.sLogger::debug);
		} else {
			return noopLogger;
		}
	}
	public JsonLogger info() {
		if (sLogger.isInfoEnabled()) {
			return new StdJsonLogger(json, INFO, this.sLogger::info, maxLogLength, this.sLogger::info);
		} else {
			return noopLogger;
		}
	}
	public JsonLogger warn() {
		if (sLogger.isWarnEnabled()) {
			return new StdJsonLogger(json, WARN, this.sLogger::warn, maxLogLength, this.sLogger::warn);
		} else {
			return noopLogger;
		}
	}
	public JsonLogger error() {
		if (sLogger.isErrorEnabled()) {
			return new StdJsonLogger(json, ERROR, this.sLogger::error, maxLogLength, this.sLogger::error);
		} else {
			return noopLogger;
		}
	}
	public boolean isTraceEnabled() {
		return this.sLogger.isTraceEnabled();
	}
	public boolean isDebugEnabled() {
		return this.sLogger.isDebugEnabled();
	}
	public boolean isInfoEnabled() {
		return this.sLogger.isInfoEnabled();
	}
	public boolean isWarnEnabled() {
		return this.sLogger.isWarnEnabled();
	}
	public boolean isErrorEnabled() {
		return this.sLogger.isErrorEnabled();
	}
}
