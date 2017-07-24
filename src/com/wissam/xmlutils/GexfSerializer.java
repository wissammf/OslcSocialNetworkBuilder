package com.wissam.xmlutils;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.wissam.model.graph.gexf.Gexf;

public class GexfSerializer {
	public static void writeGexfToFile(Gexf gexf, String fileName) {
		if (fileName == null) {
			fileName = FileNames.GEXF_GRAPH_FILE_NAME;
		}
		try {
			File file = new File(fileName);
			JAXBContext jaxbContext = JAXBContext.newInstance(Gexf.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(gexf, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
