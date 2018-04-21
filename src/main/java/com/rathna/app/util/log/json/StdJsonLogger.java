package com.rathna.app.util.log.json;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.MDC;
import org.slf4j.Marker;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SuppressWarnings({ "rawtypes" })
public class StdJsonLogger implements JsonLogger {
	private static final FastDateFormat FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSSZ");
	private final ObjectMapper json;
	private final Consumer<String> operation;
	private final BiConsumer<Marker, String> operationWithMarker;
	private final String level;
	private final ObjectNode msgNode;
	private final int maxLogLength;
	private Marker marker;

	public StdJsonLogger(ObjectMapper json, String level, Consumer<String> operation, int maxLogLength,
			BiConsumer<Marker, String> operationWithMarker) {
		this.json = json;
		this.level = level;
		this.operation = operation;
		this.operationWithMarker = operationWithMarker;
		this.msgNode = json.createObjectNode();
		this.maxLogLength = maxLogLength;
	}
	@Override
	public JsonLogger message(String message) {
		return tc("message", json.valueToTree(message));
	}
	@Override
	public JsonLogger message(Supplier<String> message) {
		return tc("message", json.valueToTree(message.get()));
	}
	@Override
	public JsonLogger map(String key, Map map) {
		return tc(key, json.valueToTree(map));
	}
	@Override
	public JsonLogger map(String key, Supplier<Map> map) {
		return tc(key, json.valueToTree(map.get()));
	}
	@Override
	public JsonLogger list(String key, List list) {
		return tc(key, json.valueToTree(list));
	}
	@Override
	public JsonLogger list(String key, Supplier<List> list) {
		return tc(key, json.valueToTree(list.get()));
	}
	@Override
	public JsonLogger field(String key, Object val) {
		return tc(key, json.valueToTree(val));
	}
	@Override
	public JsonLogger field(String key, Supplier val) {
		if (val == null) {
			return tc(key, msgNode.nullNode());
		}
		return tc(key, json.valueToTree(val.get()));
	}
	@Override
	public JsonLogger fields(Map<String, Object> fields) {
		if (fields == null || fields.isEmpty()) {
			return this;
		}
		Iterator<String> iterator = fields.keySet().iterator();
		while (iterator.hasNext()) {
			String next = iterator.next();
			this.field(next, fields.get(next));
		}
		return this;
	}
	@Override
	public JsonLogger json(String key, ObjectNode jsonElem) {
		return tc(key, json.valueToTree(jsonElem));
	}
	@Override
	public JsonLogger json(String key, Supplier<ObjectNode> jsonElem) {
		return tc(key, json.valueToTree(jsonElem.get()));
	}
	@Override
	public JsonLogger exception(String key, Throwable jsonElem) {
		return tc(key, json.valueToTree(formatException(jsonElem)));
	}
	@Override
	public JsonLogger exception(Throwable jsonElem) {
		return exception("exception", jsonElem);
	}
	@Override
	public JsonLogger stack() {
		return tc("stacktracee", json.valueToTree(formatStack()));
	}
	@Override
	public JsonLogger marker(Marker marker) {
		this.marker = marker;
		return tc("marker", json.valueToTree(marker));
	}
	@Override
	public void log() {
		if (marker == null) {
			this.operation.accept(formatMsg());
		} else {
			this.operationWithMarker.accept(marker, formatMsg());
		}
	}
	/**
	 * The final json as string to be logged.
	 * Visibility is set to {@code public} for testing
	 * @return
	 */
	public String formatMsg() {
		try {
			ObjectNode rootNode = json.createObjectNode();
			makeMetaNode(rootNode);
			makeMdcNode(rootNode);
			makeMsgNode(rootNode);
			rootNode.set("msg", msgNode);
			return StringUtils.left(json.writeValueAsString(rootNode), maxLogLength);
		} catch (Exception e) {
			return "{\"error\":\"Exception serializing" + formatException(e) + "\"}";
		}
	}
	private void makeMsgNode(ObjectNode rootNode) {
		rootNode.set("msg", rootNode);
	}
	private void makeMdcNode(ObjectNode rootNode) {
		Map<String, String> mdc = MDC.getCopyOfContextMap();
		if (mdc != null) {
			if (!mdc.isEmpty()) {
				rootNode.set("mdc", json.valueToTree(mdc));
			}
		}
	}
	private void makeMetaNode(ObjectNode rootNode) {
		ObjectNode metaNode = json.createObjectNode();
		metaNode.set("thread", json.valueToTree(Thread.currentThread().getName()));
		metaNode.set("ts", json.valueToTree(FORMATTER.format(System.currentTimeMillis())));
		metaNode.set("level", json.valueToTree(level));
		rootNode.set("meta", metaNode);
	}
	private String formatException(Throwable e) {
		return ExceptionUtils.getStackTrace(e);
	}
	private String formatStack() {
		return formatException(new Exception());
	}
	private JsonLogger tc(String key, Object obj) {
		try {
			msgNode.set(key, json.valueToTree(obj));
		} catch (Exception e) {
			msgNode.set(key, json.valueToTree(formatException(e)));
		}
		return this;
	}
}
