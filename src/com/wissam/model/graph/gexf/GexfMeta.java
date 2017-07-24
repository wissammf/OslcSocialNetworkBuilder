package com.wissam.model.graph.gexf;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class GexfMeta {
	private String lastModifiedDate;
	private String creator;
	private String description;

	public GexfMeta(String lastmodifieddate, String creator, String description) {
		this.lastModifiedDate = lastmodifieddate;
		this.creator = creator;
		this.description = description;
	}

	@XmlAttribute(name = "lastmodifieddate")
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@XmlElement(name = "creator")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
