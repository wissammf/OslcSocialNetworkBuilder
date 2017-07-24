package com.wissam.model.changerequest;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;

import com.wissam.model.CoreResource;
import com.wissam.model.ElementWithResource;

@XmlRootElement(name = "ChangeRequest", namespace = "http://open-services.net/xmlns/cm/1.0/")
public class ChangeRequest extends CoreResource {
	private String identifier;
	private String title;
	private List<ElementWithResource> relatedChangeRequests;
	private List<ElementWithResource> testedByTestCase;
	private boolean closed;
	private boolean inprogress;
	private boolean fixed;
	private boolean approved;
	private boolean reviewed;
	private boolean verified;

	@XmlAttribute(namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#", name = "about")
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@XmlElement(name = "identifier")
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@XmlElement(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	@XmlElement(name = "testedByTestCase")
	public List<ElementWithResource> getTestedByTestCase() {
		return testedByTestCase;
	}

	public void setTestedByTestCase(List<ElementWithResource> testedByTestCase) {
		this.testedByTestCase = testedByTestCase;
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

	@XmlAttribute(name = "closed")
	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	@XmlAttribute(name = "inprogress")
	public boolean isInprogress() {
		return inprogress;
	}

	public void setInprogress(boolean inprogress) {
		this.inprogress = inprogress;
	}

	@XmlAttribute(name = "fixed")
	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	@XmlAttribute(name = "approved")
	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	@XmlAttribute(name = "reviewed")
	public boolean isReviewed() {
		return reviewed;
	}

	public void setReviewed(boolean reviewed) {
		this.reviewed = reviewed;
	}

	@XmlAttribute(name = "verified")
	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}
}
