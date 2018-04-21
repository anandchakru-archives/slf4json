package com.rathna.app.util.log.json;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import org.slf4j.Marker;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SuppressWarnings("rawtypes")
public interface JsonLogger {
	default JsonLogger message(String message) {
		return this;
	};
	default JsonLogger message(Supplier<String> message) {
		return this;
	}
	default JsonLogger map(String key, Map map) {
		return this;
	}
	default JsonLogger map(String key, Supplier<Map> map) {
		return this;
	}
	default JsonLogger list(String key, List list) {
		return this;
	}
	default JsonLogger list(String key, Supplier<List> list) {
		return this;
	}
	default JsonLogger field(String key, Object val) {
		return this;
	}
	default JsonLogger field(String key, Supplier val) {
		return this;
	}
	default JsonLogger fields(Map<String, Object> fields) {
		return this;
	}
	default JsonLogger json(String key, ObjectNode jsonElem) {
		return this;
	}
	default JsonLogger json(String key, Supplier<ObjectNode> jsonElem) {
		return this;
	}
	default JsonLogger exception(String key, Throwable jsonElem) {
		return this;
	}
	default JsonLogger exception(Throwable jsonElem) {
		return this;
	}
	default JsonLogger stack() {
		return this;
	}
	default JsonLogger marker(Marker jsonElem) {
		return this;
	}
	default void log() {
	}
	default String formatMsg() {
		return "";
	}
}
