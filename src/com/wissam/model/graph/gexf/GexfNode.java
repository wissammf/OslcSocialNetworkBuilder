package com.wissam.model.graph.gexf;

import javax.xml.bind.annotation.XmlAttribute;

public class GexfNode {
	private String id;
	private String label;

	public GexfNode(String id, String label) {
		super();
		this.id = id;
		this.label = label;
	}

	@XmlAttribute(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlAttribute(name = "label")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
