package com.pay360.poc.jpath.pathwriter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ArrayElement extends PathElement {

	private String fieldName;

	public ArrayElement(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public JsonNode get(JsonNode parentNode) {
		return parentNode.path(fieldName);
	}

	@Override
	public void set(JsonNode parentNode, JsonNode value) {
	}

	@Override
	public JsonNode create(JsonNode parentNode) {
		return ((ObjectNode) parentNode).putArray(fieldName);
	}
}
