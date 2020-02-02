package com.pay360.poc.jpath.pathwriter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JPath {
	private static final Pattern pathElementFormat = Pattern.compile("([a-zA-Z_]\\w*)((?:\\[\\d*\\])*)");
	private static final Pattern arrayIndexFormat = Pattern.compile("\\[(\\d*)\\]");
	private static final ObjectMapper mapper = new ObjectMapper();

	private JPath() {
	}
	
	public static boolean exists(JsonNode node, String path) {
		
		return false;
	}
	
	public static void setOrCreate(JsonNode node, String path, Object value) {

		Deque<PathElement> elements = new ArrayDeque<>(JPath.parsePath(path));

		if(elements.isEmpty()) {
			
			return;
		}

		JsonNode currentNode = node;
		
		while(elements.size() > 1) {
			JsonNode elementNode = elements.getFirst().get(currentNode);
			if(elementNode.isMissingNode()) {
				break;
			}
			elements.removeFirst();
			currentNode = elementNode;
		}

		while(elements.size() > 1) {
			JsonNode elementNode = elements.getFirst().create(currentNode);
			elements.removeFirst();
			currentNode = elementNode;
		}

		elements.getFirst().set(currentNode, mapper.valueToTree(value));
	}

	public static Object get(JsonNode node, String path) {

		Deque<PathElement> elements = new ArrayDeque<>(JPath.parsePath(path));

		if(elements.isEmpty()) {
			
			return null;
		}

		JsonNode currentNode = node;
		
		while(elements.size() > 1) {
			JsonNode elementNode = elements.getFirst().get(currentNode);
			if(elementNode.isMissingNode()) {

				return null;
			}
			elements.removeFirst();
			currentNode = elementNode;
		}

		return elements.getFirst().get(currentNode);
	}
	
	public static List<PathElement> parsePath(String path) {

		return Arrays.asList(path.split("\\.")).stream().map(JPath::parseElement).flatMap(List::stream).collect(Collectors.toList());
	}
	
	private static List<PathElement> parseElement(String element) {

		Matcher m = pathElementFormat.matcher(element);

		if(!m.matches()) {
			throw new JPathReferenceException("Bad format for JPath element");
		}

		List<PathElement> elementList = new ArrayList<>();
		
		if(m.group(1) != null) {
			if(m.group(2) != null && !m.group(2).isEmpty()) {
				elementList.add(new ArrayElement(m.group(1)));
				elementList.addAll(parseArrayIndexes(m.group(2)).stream().map(index -> new ArrayItemElement(index.isEmpty() ? -1 : Integer.parseInt(index))).collect(Collectors.toList()));
			}
			else {
				elementList.add(new ObjectElement(m.group(1)));
			}
		}

		return elementList;
	}
	
	private static List<String> parseArrayIndexes(String indexes) {
		
		Matcher m = arrayIndexFormat.matcher(indexes);
		List<String> r = new ArrayList<>();
		
		while(m.find()) {
			r.add(m.group(1));
		}
		
		return r;
	}
}
