package com.wissam.model.testcase;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RDF")
public class TestCaseRdf {
	private TestCase testCase;

	@XmlElement(name = "TestCase")
	public TestCase getTestCase() {
		return testCase;
	}

	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}
}
