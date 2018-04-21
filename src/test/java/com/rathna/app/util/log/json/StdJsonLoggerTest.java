package com.rathna.app.util.log.json;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rathna.app.testutil.JsonValidatorUtil;

class StdJsonLoggerTest {
	private org.slf4j.Logger sLogger = org.slf4j.LoggerFactory.getLogger(StdJsonLoggerTest.class);
	private ObjectMapper json = new ObjectMapper();
	int maxLogLength = Integer.MAX_VALUE;
	public static final String ERROR = "ERROR";
	private JsonLogger std = null;

	@BeforeEach
	public void setup() {
		std = new StdJsonLogger(json, ERROR, this.sLogger::error, maxLogLength, this.sLogger::error);
	}
	@Test
	void testFormatMsg() {
		String json = std.field("key1", "value1").formatMsg();
		assertTrue("Valid Json", JsonValidatorUtil.isJSONValid(json));
		assertThat(json, containsString("key1"));
		assertThat(json, containsString("value1"));
	}
}
