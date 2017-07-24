package com.wissam.model.testcase;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;

import com.wissam.model.CoreResource;
import com.wissam.model.ElementWithResource;

public class TestCase extends CoreResource {
	public static final String CORRECT_TYPE = "http://open-services.net/ns/qm#TestCase";

	private String identifier;
	private String title;
	private List<ElementWithResource> relatedChangeRequests;
	private List<ElementWithResource> testsChangeRequest;

	@XmlAttribute(namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#", name = "about")
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@XmlElement(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlElement(name = "identifier")
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@XmlElement(name = "creator")
	public ElementWithResource getCreator() {
		return creator;
	}

	public void setCreator(ElementWithResource creator) {
		this.creator = creator;
	}

	@XmlElement(name = "contributor")
	public ElementWithResource getContributor() {
		return contributor;
	}

	public void setContributor(ElementWithResource contributor) {
		this.contributor = contributor;
	}

	@XmlElement(name = "modifiedBy")
	public ElementWithResource getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(ElementWithResource modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@XmlElement(name = "relatedChangeRequest")
	public List<ElementWithResource> getRelatedChangeRequests() {
		return relatedChangeRequests;
	}

	public void setRelatedChangeRequests(List<ElementWithResource> relatedChangeRequests) {
		this.relatedChangeRequests = relatedChangeRequests;
	}

	@XmlElement(name = "testsChangeRequest")
	public List<ElementWithResource> getTestsChangeRequest() {
		return testsChangeRequest;
	}

	public void setTestsChangeRequest(List<ElementWithResource> testsChangeRequest) {
		this.testsChangeRequest = testsChangeRequest;
	}

	@XmlElement(name = "created")
	@XmlSchemaType(name = "date")
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@XmlElement(name = "modified")
	@XmlSchemaType(name = "date")
	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@XmlAttribute(name = "subject")
	public String getSubject() {
		return subject;
	}

	@Override
	public void setSubject(String subject) {
		this.subject = subject;
	}
}
