package com.wissam.model.graph.gexf;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "gexf")
@XmlType(propOrder = { "meta", "graph" })
public class Gexf {
	private String version;
	private GexfMeta meta;
	private GexfGraph graph;

	public Gexf() {
		setVersion("1.2");
	}

	@XmlAttribute(name = "version")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@XmlElement(name = "meta")
	public GexfMeta getMeta() {
		return meta;
	}

	public void setMeta(GexfMeta meta) {
		this.meta = meta;
	}

	@XmlElement(name = "graph")
	public GexfGraph getGraph() {
		return graph;
	}

	public void setGraph(GexfGraph graph) {
		this.graph = graph;
	}
}
