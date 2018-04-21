package com.rathna.app.testutil;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonValidatorUtil {
	public static boolean isJSONValid(String jsonInString) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.readTree(jsonInString);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
