package com.rathna.app.util.log.json;

public class LoggerFactory {
	public static Logger getLogger(String name, int maxLogLength) {
		return new Logger(org.slf4j.LoggerFactory.getLogger(name), maxLogLength);
	}
	public static Logger getLogger(Class<?> clazz, int maxLogLength) {
		return new Logger(org.slf4j.LoggerFactory.getLogger(clazz), maxLogLength);
	}
}