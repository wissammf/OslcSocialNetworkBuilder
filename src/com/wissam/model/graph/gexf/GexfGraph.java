package com.wissam.model.graph.gexf;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "mode", "defaultEdgeType", "nodes", "edges" })
public class GexfGraph {
	private String mode;
	private String defaultEdgeType;
	private List<GexfNode> nodes;
	private List<GexfEdge> edges;

	public GexfGraph() {
		setMode("static");
		setDefaultEdgeType("directed");
	}

	@XmlAttribute(name = "mode")
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	@XmlAttribute(name = "defaultedgetype")
	public String getDefaultEdgeType() {
		return defaultEdgeType;
	}

	public void setDefaultEdgeType(String defaultEdgeType) {
		this.defaultEdgeType = defaultEdgeType;
	}

	@XmlElementWrapper(name = "nodes")
	@XmlElement(name = "node")
	public List<GexfNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<GexfNode> nodes) {
		this.nodes = nodes;
	}

	@XmlElementWrapper(name = "edges")
	@XmlElement(name = "edge")
	public List<GexfEdge> getEdges() {
		return edges;
	}

	public void setEdges(List<GexfEdge> edges) {
		this.edges = edges;
	}

}
