package com.wissam.model;

import java.util.Objects;

public class RelationshipDestinationPair {
	private Relationship relationship;
	private CoreResource destination;

	public RelationshipDestinationPair(Relationship relationship, CoreResource destination) {
		super();
		this.relationship = relationship;
		this.destination = destination;
	}

	public Relationship getRelationship() {
		return relationship;
	}

	public CoreResource getDestination() {
		return destination;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof RelationshipDestinationPair)) {
			return false;
		}
		RelationshipDestinationPair pair = (RelationshipDestinationPair) obj;
		return this.relationship == pair.relationship && Objects.equals(this.destination, pair.destination);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.relationship, this.destination);
	}
}
