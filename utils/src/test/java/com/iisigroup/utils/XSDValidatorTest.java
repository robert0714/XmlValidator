package com.iisigroup.utils;

import static org.junit.Assert.*;

import com.sun.msv.grammar.xmlschema.XMLSchemaGrammar;
import com.sun.msv.reader.GrammarReaderController2;
import com.sun.msv.reader.xmlschema.WSDLSchemaReader; 

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory; 
import javax.xml.transform.dom.DOMSource; 

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class XSDValidatorTest {
	XSDValidator component;

	@Before
	public void setUp() throws Exception {
		component = new XSDValidator();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidateXMLSchema00() throws Exception {
		InputStream xml = this.getClass().getResourceAsStream(
				"xmlsample/students.xml");
		boolean validation = component.validateXMLSchema(xml);
		Assert.assertTrue(validation);
	}
	@Test
	public void testValidateXMLSchemaInputStream00() throws Exception {
		InputStream xml = this.getClass().getResourceAsStream(
				"xmlsample/multireference.wsdl");
		boolean validation = component.validateXMLSchema(xml);
		Assert.assertTrue(validation);
	} 
	@Test
	public void testValidateXMLSchemaInputStream01() throws Exception {
		InputStream xml = this.getClass().getResourceAsStream(
				"xmlsample/test.wsdl");
		boolean validation = component.validateXMLSchema(xml);
		Assert.assertTrue(validation);
	} 
	@Test
	public void testValidateXMLSchemaInputStream02() throws Exception {
		InputStream xml = this.getClass().getResourceAsStream(
				"xmlsample/students.xml");
		boolean validation = component.validateXMLSchema(xml);
		Assert.assertTrue(validation);
	} 
	@Test
	public void testValidateXMLSchemaInputStream03() throws Exception {
		InputStream xml = this.getClass().getResourceAsStream(
				"xmlsample/1208-1.wsdl");
		boolean validation = component.validateXMLSchema(xml);
		Assert.assertTrue(validation);
	} 
	
	@Test
	public void testValidateXMLSchemaInputStream04() throws Exception {
		InputStream xml = this.getClass().getResourceAsStream(
				"xmlsample/bugfixes/CannotLocateDTD/InvalidHost.xml");
		boolean validation = component.validateXMLSchema(xml);
		Assert.assertFalse(validation);
	} 
	
	@Test
	public void testValidateXMLSchemaInputStream05() throws Exception {
		InputStream xml = this.getClass().getResourceAsStream(
				"xmlsample/bugfixes/CannotLocateDTD/InvalidLocation.xml");
		boolean validation = component.validateXMLSchema(xml);
		Assert.assertFalse(validation);
	} 
	@Test
	public void testValidateXMLSchemaInputStream06() throws Exception {
		InputStream xml = this.getClass().getResourceAsStream(
				"xmlsample/bugfixes/CustomErrorReportedOnCorrectElement/simplenested.xml");
		boolean validation = component.validateXMLSchema(xml);
		Assert.assertTrue(validation);
	} 
	@Test
	public void testValidateXMLSchemaInputStream07() throws Exception {
		InputStream xml = this.getClass().getResourceAsStream(
				"xmlsample/bugfixes/EmptyFile/Empty.xml");
		boolean validation = component.validateXMLSchema(xml);
		Assert.assertFalse(validation);
	} 
	@Test
	public void testValidateXMLSchemaInputStream08() throws Exception {
		InputStream xml = this.getClass().getResourceAsStream(
				"xmlsample/bugfixes/NoGrammar/NoGrammar.xml");
		boolean validation = component.validateXMLSchema(xml);
		Assert.assertTrue(validation);
	} 
	@Test
	public void testValidateXMLSchemaInputStream09() throws Exception {
		InputStream xml = this.getClass().getResourceAsStream(
				"xmlsample/bugfixes/NotWellFormed/NotWellFormed.xml");
		boolean validation = component.validateXMLSchema(xml);
		Assert.assertFalse(validation);
	} 
	@Test
	public void testValidateXMLSchemaInputStream10() throws Exception {
		InputStream xml = this.getClass().getResourceAsStream(
				"xmlsample/bugfixes/RootNoNSChildNS/RootNoNSChildNS.xml");
		boolean validation = component.validateXMLSchema(xml);
		Assert.assertTrue(validation);
	} 
	
	@Test
	@Ignore
	public void testValidateXMLSchemaInputStream11() throws Exception {
		InputStream xml01 = this.getClass().getResourceAsStream(
				"xmlsample/bugfixes/ValidateWithDTD/ValidateWithDTDInvalid.xml");
		InputStream xml02 = this.getClass().getResourceAsStream(
				"xmlsample/bugfixes/ValidateWithDTD/ValidateWithDTDValid.xml");
		InputStream dtd01 = this.getClass().getResourceAsStream(
				"xmlsample/bugfixes/ValidateWithDTD/simple.dtd");
		
		boolean validation02 = component.validateXMLSchema(xml02,dtd01);
		Assert.assertTrue(validation02);
		
		boolean validation01 = component.validateXMLSchema(xml01,dtd01);
		Assert.assertFalse(validation01);
	} 
	
	@Test 
	public void testValidateXMLSchema04() throws Exception {
		InputStream xml = this.getClass().getResourceAsStream(
				"xmlsample/bugfixes/NoNamespaceSchema/NoNamespaceSchema.xml");
		InputStream xsd = this.getClass().getResourceAsStream(
				"xmlsample/bugfixes/NoNamespaceSchema/NoNamespaceSchema.xsd");
		
		boolean validation = component.validateXMLSchema(xml, xsd);
		Assert.assertFalse(validation);
	}
	
	
	
	@Test 
	public void testValidateXMLSchema03() throws Exception {
		InputStream xml = this.getClass().getResourceAsStream(
				"xmlsample/bugfixes/CustomErrorReportedOnCorrectElement/simplenested.xml");
		InputStream xsd = this.getClass().getResourceAsStream(
				"xmlsample/bugfixes/CustomErrorReportedOnCorrectElement/simplenested.xsd");
		boolean validation = component.validateXMLSchema(xml, xsd);
		Assert.assertFalse(validation);
	}
	
	@Test
	public void testValidateXMLSchema01() throws Exception {
		InputStream xml = this.getClass().getResourceAsStream(
				"xmlsample/students.xml");
		InputStream xsd = this.getClass().getResourceAsStream(
				"xmlsample/students.xsd");
		boolean validation = component.validateXMLSchema(xml, xsd);
		Assert.assertTrue(validation);
	}

	@Test
	public void testValidateXMLSchema02() throws Exception {
		InputStream xml = this.getClass().getResourceAsStream(
				"xmlsample/1208-1.wsdl");
		InputStream xsd01 = this.getClass().getResourceAsStream(
				"xmlsample/wsdl.xsd");
		InputStream xsd02 = this.getClass().getResourceAsStream(
				"xmlsample/soap.xsd");
		InputStream xsd03 = this.getClass().getResourceAsStream(
				"xmlsample/soap12.xsd");
		boolean validation = component.validateXMLSchema(xml, xsd01, xsd02,
				xsd03);
		Assert.assertTrue(validation);
	}

	
	
	
	
	@Test
	public void testWsdlMultiSchema() throws Exception {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
		URL wsdlUri = getClass().getResource("xmlsample/test.wsdl");
		assertNotNull(wsdlUri);
		Document wsdl = documentBuilder.parse(wsdlUri.openStream());
		String wsdlSystemId = wsdlUri.toExternalForm();
		DOMSource source = new DOMSource(wsdl);
		source.setSystemId(wsdlSystemId);

		LocalController controller = new LocalController();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XMLSchemaGrammar result = WSDLSchemaReader.read(source, factory,
				controller);
		assertNotNull(result);
	}

	@Test
	public void testWsdlMultiRefSchema() throws Exception {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
		URL wsdlUri = getClass().getResource("xmlsample/multireference.wsdl");
		assertNotNull(wsdlUri);
		Document wsdl = documentBuilder.parse(wsdlUri.openStream());
		String wsdlSystemId = wsdlUri.toExternalForm();
		DOMSource source = new DOMSource(wsdl);
		source.setSystemId(wsdlSystemId);

		LocalController controller = new LocalController();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XMLSchemaGrammar result = WSDLSchemaReader.read(source, factory,
				controller);
		assertNotNull(result);
	}
	
	
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
    }
	
}
