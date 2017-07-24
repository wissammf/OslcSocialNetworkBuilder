package com.wissam.model.graph;

import java.util.HashMap;

public class Node {
	private static int idCounter = 0;

	private String id;
	private String uri;
	private HashMap<String, Edge> outgoingEdges;

	public Node(String uri) {
		super();

		this.id = String.valueOf(idCounter);
		idCounter++;

		this.uri = uri;

		outgoingEdges = new HashMap<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public HashMap<String, Edge> getOutgoingEdges() {
		return outgoingEdges;
	}

	public void setOutgoingEdges(HashMap<String, Edge> outgoingEdges) {
		this.outgoingEdges = outgoingEdges;
	}

	@Override
	public String toString() {
		String result = "Node " + id + " with label " + uri.substring(uri.lastIndexOf('/') + 1) + "\n";

		for (HashMap.Entry<String, Edge> edge : outgoingEdges.entrySet()) {
			result += edge.getValue().toString() + "\n";
		}

		return result;
	}
}
