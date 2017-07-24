package com.wissam.model.graph;

import java.util.LinkedList;
import java.util.List;

import com.wissam.model.RelationshipDestinationPair;

public class Edge {
	private static int idCounter = 0;

	private String id;
	private Node destination;
	private int weight;
	private List<List<RelationshipDestinationPair>> paths;

	public Edge(Node destination, List<RelationshipDestinationPair> path) {
		super();

		this.id = String.valueOf(idCounter);
		idCounter++;

		this.weight = 1;

		paths = new LinkedList<>();

		this.destination = destination;
		addPath(path);
	}

	private void addPath(List<RelationshipDestinationPair> path) {
		// If the path exists return
		for (List<RelationshipDestinationPair> existingPath : paths) {
			if (existingPath.equals(path)) {
				return;
			}
		}

		this.paths.add(path);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Node getDestination() {
		return destination;
	}

	public void setDestination(Node destination) {
		this.destination = destination;
	}

	public int getWeight() {
		return weight;
	}

	public void incrementWeight(List<RelationshipDestinationPair> path) {
		this.weight++;

		addPath(path);
	}

	public List<List<RelationshipDestinationPair>> getPaths() {
		return paths;
	}

	@Override
	public String toString() {
		return "Edge " + id + " to " + destination.getUri().substring(destination.getUri().lastIndexOf('/') + 1)
				+ ", weight " + weight;
	}
}
