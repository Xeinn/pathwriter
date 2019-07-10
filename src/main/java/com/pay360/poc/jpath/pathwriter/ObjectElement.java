package com.pay360.poc.jpath.pathwriter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ObjectElement extends PathElement {

	private String fieldName;

	public ObjectElement(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public JsonNode get(JsonNode parentNode) {
		return parentNode.path(fieldName);
	}

	@Override
	public void set(JsonNode parentNode, JsonNode value) {
		((ObjectNode) parentNode).set(fieldName, value);
	}

	@Override
	public JsonNode create(JsonNode parentNode) {
		return ((ObjectNode) parentNode).putObject(fieldName);
	}
}
