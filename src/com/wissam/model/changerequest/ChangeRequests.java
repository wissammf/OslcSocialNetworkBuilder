package com.wissam.model.changerequest;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Collection")
public class ChangeRequests {
	private List<ChangeRequest> changeRequests;

	@XmlElement(name = "ChangeRequest")
	public List<ChangeRequest> getChangeRequests() {
		return changeRequests;
	}

	public void setChangeRequests(List<ChangeRequest> changeRequests) {
		this.changeRequests = changeRequests;
	}
}
