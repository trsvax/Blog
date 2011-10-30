package com.trsvax.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;


public class XMLImpl  implements XML {
	private final Logger logger;
	private final ApplicationGlobals globals;
	private final TransformerFactory factory = TransformerFactory.newInstance();
	
	public XMLImpl(Logger logger,ApplicationGlobals globals) {
		this.logger = logger;
		this.globals = globals;
	}

	public StreamResponse streamWithStyle(final Object object, final String mimetype, final String xslt,
			final Map<String, String> args) {
				
		return new StreamResponse() {
			InputStream inputStream;
			
			public void prepareResponse(Response response) {
				ByteArrayOutputStream os = transform(java2xml(object), xslt, args);
				response.setContentLength(os.size());
				inputStream = new ByteArrayInputStream(os.toByteArray());
			}
			
			public InputStream getStream() throws IOException {
				return inputStream ;
			}
			
			public String getContentType() {
				return mimetype == null ? "text/xml" : mimetype;
			}
		};
	}
	
	public ByteArrayOutputStream java2xml(Object object) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
			jaxbContext.createMarshaller().marshal(object, out);
			return out;
		} catch (Exception e) {
			logger.error("{}",e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public ByteArrayOutputStream transform(ByteArrayOutputStream os, String xslt, Map<String, String> args)  {
		if ( xslt == null ) {
			return os;
		}
		try {
			StreamSource source = new StreamSource(new ByteArrayInputStream(os.toByteArray()));			
			StreamSource styleSource = new StreamSource(globals.getServletContext().getResourceAsStream(xslt));					
			ByteArrayOutputStream out = new ByteArrayOutputStream();			
			StreamResult result = new StreamResult(out);
			Transformer transformer = factory.newTransformer(styleSource);
			transformer.transform(source, result);		
			return out;
		} catch ( Exception e) {
			logger.error("{}",e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}


}
