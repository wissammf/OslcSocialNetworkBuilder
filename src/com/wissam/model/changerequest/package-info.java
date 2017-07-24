@XmlSchema(namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#", elementFormDefault = XmlNsForm.QUALIFIED,
		// namespace="http://open-services.net/xmlns/cm/1.0/",
		// elementFormDefault=XmlNsForm.QUALIFIED,
		xmlns = { @XmlNs(prefix = "rdf", namespaceURI = "http://www.w3.org/1999/02/22-rdf-syntax-ns#"),
				@XmlNs(prefix = "dcterms", namespaceURI = "http://purl.org/dc/terms/"),
				@XmlNs(prefix = "oslc_qm", namespaceURI = "http://open-services.net/ns/qm#"),
				@XmlNs(prefix = "oslc_cm", namespaceURI = "http://open-services.net/xmlns/cm/1.0/"),
				@XmlNs(prefix = "oslc", namespaceURI = "http://open-services.net/ns/core#")

		})

package com.wissam.model.changerequest;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;