package com.pay360.poc.jpath.pathwriter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class ArrayItemElement extends PathElement {

	private int arrayIndex;

	public ArrayItemElement(int arrayIndex) {
		this.arrayIndex = arrayIndex;
	}

	@Override
	public JsonNode get(JsonNode parentNode) {
		return parentNode.path(arrayIndex);
	}

	@Override
	public void set(JsonNode parentNode, JsonNode value) {
		if(arrayIndex < 0 || arrayIndex >= parentNode.size()) {
			((ArrayNode) parentNode).add(value);
		}
		else {
			((ArrayNode) parentNode).set(arrayIndex, value);
		}
	}

	@Override
	public JsonNode create(JsonNode parentNode) {
		if(arrayIndex < 0 || arrayIndex >= parentNode.size()) {
			return ((ArrayNode) parentNode).addArray();
		}
		else {
			return ((ArrayNode) parentNode).insertArray(arrayIndex);
		}
	}
}
