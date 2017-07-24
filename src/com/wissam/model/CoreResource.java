package com.wissam.model;

import java.util.Date;
import java.util.Objects;

public abstract class CoreResource {
	protected String uri;
	protected ElementWithResource creator;
	protected ElementWithResource contributor;
	protected ElementWithResource modifiedBy;
	protected Date created;
	protected Date modified;
	protected String subject;

	public abstract String getUri();

	public abstract void setUri(String uri);

	public abstract ElementWithResource getCreator();

	public abstract void setCreator(ElementWithResource creator);

	public abstract ElementWithResource getContributor();

	public abstract void setContributor(ElementWithResource contributor);

	public abstract ElementWithResource getModifiedBy();

	public abstract void setModifiedBy(ElementWithResource modifiedBy);

	public abstract Date getCreated();

	public abstract void setCreated(Date created);

	public abstract Date getModified();

	public abstract void setModified(Date modified);

	public abstract String getSubject();

	public abstract void setSubject(String subject);

	@Override
	public boolean equals(Object obj) {

		if (obj == this)
			return true;
		if (!(obj instanceof CoreResource)) {
			return false;
		}
		CoreResource resource = (CoreResource) obj;
		return Objects.equals(this.uri, resource.uri) && Objects.equals(this.creator, resource.creator)
				&& Objects.equals(this.contributor, resource.contributor)
				&& Objects.equals(this.modifiedBy, resource.modifiedBy)
				&& Objects.equals(this.created, resource.created) && Objects.equals(this.modified, resource.modified)
				&& Objects.equals(this.subject, resource.subject);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.uri, this.creator, this.contributor, this.modifiedBy, this.created, this.modified,
				this.subject);
	}
}
