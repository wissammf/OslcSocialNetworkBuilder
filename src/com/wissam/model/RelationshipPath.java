package com.wissam.model;

import java.util.LinkedList;

public class RelationshipPath {
	private LinkedList<RelationshipDestinationPair> steps;

	public RelationshipPath() {
		super();
		this.steps = new LinkedList<>();
	}

	public LinkedList<RelationshipDestinationPair> getSteps() {
		return steps;
	}
}
