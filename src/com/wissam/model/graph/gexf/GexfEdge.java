package com.wissam.model.graph.gexf;

import javax.xml.bind.annotation.XmlAttribute;

public class GexfEdge {
	private String id;
	private String source;
	private String target;
	private int weight;

	public GexfEdge(String id, String source, String target, int weight) {
		super();
		this.id = id;
		this.source = source;
		this.target = target;
		this.weight = weight;
	}

	@XmlAttribute(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlAttribute(name = "source")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@XmlAttribute(name = "target")
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@XmlAttribute(name = "weight")
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
