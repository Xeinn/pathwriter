package com.pay360.poc.jpath.pathwriter;


import com.fasterxml.jackson.databind.JsonNode;

public abstract class PathElement {

	public abstract JsonNode get(JsonNode parentNode);
	public abstract void set(JsonNode parentNode, JsonNode value);
	public abstract JsonNode create(JsonNode parentNode);
}