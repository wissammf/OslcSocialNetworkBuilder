package com.wissam.xmlutils;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.wissam.model.changerequest.ChangeRequest;
import com.wissam.model.changerequest.ChangeRequests;
import com.wissam.model.testcase.TestCase;
import com.wissam.model.testcase.TestCaseRdf;

public class OslcDataSerializer {
	public static TestCase deserializeTestCase() {
		try {
			JAXBContext jc = JAXBContext.newInstance(TestCaseRdf.class);
			File xml = new File(FileNames.TEMP_TEST_CASE_FILE_NAME);

			Unmarshaller unmarshaller = jc.createUnmarshaller();
			TestCaseRdf rdf = (TestCaseRdf) unmarshaller.unmarshal(xml);

			return rdf.getTestCase();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	public static ChangeRequest deserializeChangeRequest() {
		try {
			JAXBContext jc = JAXBContext.newInstance(ChangeRequest.class);
			File xml = new File(FileNames.TEMP_CHANGE_REQUEST_FILE_NAME);

			Unmarshaller unmarshaller = jc.createUnmarshaller();
			ChangeRequest rdf = (ChangeRequest) unmarshaller.unmarshal(xml);

			return rdf;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	public static ChangeRequests deserializeChangeRequests() {
		try {
			JAXBContext jc = JAXBContext.newInstance(ChangeRequests.class);
			File xml = new File(FileNames.CHANGE_REQUESTS_FILE_NAME);

			Unmarshaller unmarshaller = jc.createUnmarshaller();
			ChangeRequests rdf = (ChangeRequests) unmarshaller.unmarshal(xml);

			return rdf;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return null;
	}
}
