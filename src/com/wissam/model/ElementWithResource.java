package com.wissam.model;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;

public class ElementWithResource {
	private String resourceUri;

	@XmlAttribute(name = "resource", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
	public String getResourceUri() {
		return resourceUri;
	}

	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == this)
			return true;
		if (!(obj instanceof ElementWithResource)) {
			return false;
		}
		ElementWithResource element = (ElementWithResource) obj;
		return Objects.equals(this.resourceUri, element.resourceUri);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.resourceUri);
	}
}
