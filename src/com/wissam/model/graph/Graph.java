package com.wissam.model.graph;

import java.util.HashMap;
import java.util.List;

import org.apache.jena.ext.com.google.common.base.Strings;

import com.wissam.model.RelationshipDestinationPair;

public class Graph {
	private HashMap<String, Node> nodes;

	public Graph() {
		nodes = new HashMap<>();
	}

	public HashMap<String, Node> getNodes() {
		return nodes;
	}

	public boolean addNodeForUri(String uri) {
		if (!nodes.containsKey(uri)) {
			nodes.put(uri, new Node(uri));
			return true;
		}
		return false;
	}

	public void linkNodes(String uriSource, String uriDestination, List<RelationshipDestinationPair> path) {
		// Sanity check and no linking to self check
		if (Strings.isNullOrEmpty(uriSource) || Strings.isNullOrEmpty(uriDestination)
				|| uriSource.equals(uriDestination)) {
			return;
		}

		Node source = nodes.get(uriSource);
		if (source != null) {
			if (source.getOutgoingEdges().containsKey(uriDestination)) {
				source.getOutgoingEdges().get(uriDestination).incrementWeight(path);
			} else {
				source.getOutgoingEdges().put(uriDestination, new Edge(nodes.get(uriDestination), path));
			}
		}
	}

	@Override
	public String toString() {
		String result = "Graph:\n";

		for (HashMap.Entry<String, Node> node : nodes.entrySet()) {
			result += node.getValue().toString() + "\n";
		}

		return result;
	}
}
