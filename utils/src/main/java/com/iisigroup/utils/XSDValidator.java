package com.iisigroup.utils;
 
import com.sun.msv.grammar.xmlschema.XMLSchemaGrammar;
import com.sun.msv.reader.GrammarReaderController2;
import com.sun.msv.reader.xmlschema.WSDLSchemaReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator; 

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.lang3.ArrayUtils; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * The Class XSDValidator. <br/>
 * 根據XML schema(xsd)去檢查XML(ex:wsdl)
 */
public class XSDValidator {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(XSDValidator.class);

    private static class LocalController implements GrammarReaderController2 {

        public LSResourceResolver getLSResourceResolver() {
            return null;
        }

        public void error(Locator[] locs, String errorMessage, Exception nestedException) {
            StringBuffer errors = new StringBuffer();
            for (Locator loc : locs) {
                errors.append("in " + loc.getSystemId() + " " + loc.getLineNumber() + ":"
                              + loc.getColumnNumber());
            }
            throw new RuntimeException(errors.toString(), nestedException);
        }

        public void warning(Locator[] locs, String errorMessage) {
            StringBuffer errors = new StringBuffer();
            for (Locator loc : locs) {
                errors.append("in " + loc.getSystemId() + " " + loc.getLineNumber() + ":"
                              + loc.getColumnNumber());
            }
            // no warning allowed.
            throw new RuntimeException("warning: " + errors.toString());
        }

        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            return null;
        }
    };
    /**
	 * Validate xml schema.
	 *
	 * @param xmlInputStream
	 *            the xml(ex:wsdl) input stream
	 * @return true, if successful
	 */
	public boolean validateXMLSchema(final InputStream xmlInputStream){
		
		 
		boolean result =false ;
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        
        try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();      
      
			Document wsdl = documentBuilder.parse(xmlInputStream);
			 
			URL wsdlUri =    getClass().getResource("");
			String wsdlSystemId = wsdlUri.toExternalForm();
			DOMSource source = new DOMSource(wsdl);
			source.setSystemId(wsdlSystemId);

			LocalController controller = new LocalController();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XMLSchemaGrammar result0 = WSDLSchemaReader.read(source, factory, controller);        
			Iterator<com.sun.msv.grammar.xmlschema.XMLSchemaSchema > schemas = result0.iterateSchemas();
			while (schemas.hasNext()) {
				com.sun.msv.grammar.xmlschema.XMLSchemaSchema  type =  schemas.next();
				logger.info(type.toString()); 
				
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false ;
		}
        
        result = true ;
		return result ;
	}
	/**
	 * Validate xml schema.
	 *
	 * @param xmlInputStream
	 *            the xml(ex:wsdl) input stream
	 * @param xsdInputStream
	 *            the xsd (XML schema) input stream
	 * @return true, if successful
	 */
	public boolean validateXMLSchema(final InputStream xmlInputStream,
			final InputStream... xsdInputStream) {
		javax.xml.transform.Source[] schemas = null;
		if (ArrayUtils.isNotEmpty(xsdInputStream)) {
			schemas = new Source[xsdInputStream.length];
			for (int i = 0; i < xsdInputStream.length; ++i) {
				schemas[i] = new javax.xml.transform.stream.StreamSource(
						xsdInputStream[i]);
			}
		}else{
			schemas = new javax.xml.transform.Source[0];
			return validateXMLSchema(xmlInputStream);
		}
		try {
			SchemaFactory factory = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

			Schema schema = factory.newSchema(schemas);
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(xmlInputStream));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return false;
		} catch (SAXException e1) {
			logger.error(e1.getMessage(), e1);
			return false;
		}
		return true;
	}
}