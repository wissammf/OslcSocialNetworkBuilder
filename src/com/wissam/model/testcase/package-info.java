@XmlSchema(namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#", elementFormDefault = XmlNsForm.QUALIFIED, xmlns = {
		@XmlNs(prefix = "rdf", namespaceURI = "http://www.w3.org/1999/02/22-rdf-syntax-ns#"),
		@XmlNs(prefix = "dcterms", namespaceURI = "http://purl.org/dc/terms/"),
		@XmlNs(prefix = "oslc_qm", namespaceURI = "http://open-services.net/ns/qm#"),
		@XmlNs(prefix = "oslc", namespaceURI = "http://open-services.net/ns/core#") })

package com.wissam.model.testcase;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;