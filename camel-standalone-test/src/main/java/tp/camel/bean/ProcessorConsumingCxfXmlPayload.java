package tp.camel.bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.apache.camel.Exchange;
import org.apache.camel.component.cxf.CxfPayload;
import org.apache.camel.converter.jaxp.XmlConverter;
import org.apache.cxf.binding.soap.SoapHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ProcessorConsumingCxfXmlPayload {
	
	public static Logger log = LoggerFactory.getLogger(ProcessorConsumingCxfXmlPayload.class);
	
	
    public void processExchange(Exchange exchange) {
        try {
        	String namespaceURI="http://service.tp/";
			log.info("message processed by ProcessorConsumingCxfXmlPayload{}", exchange);
			System.out.println("body as string:" + exchange.getIn().getBody(String.class));
      
			CxfPayload<SoapHeader> requestPayload = exchange.getIn().getBody(CxfPayload.class);
			List<Source> inElements = requestPayload.getBodySources();
			
			XmlConverter xmlConverter = new XmlConverter();
			Element opElt = xmlConverter.toDOMElement(inElements.get(0));
			System.out.println("in local name (operation name): "+opElt.getLocalName());
			double a = Double.valueOf(opElt.getElementsByTagName("a").item(0).getTextContent());
			double b = Double.valueOf(opElt.getElementsByTagName("b").item(0).getTextContent());
			double res=a+b;
			System.out.println("addition("+a+","+b+")="+res);
		
		
			Document outDocument = xmlConverter.createDocument();
			Element responseEltNode = outDocument.createElementNS(namespaceURI, "additionResponse");
			outDocument.appendChild(responseEltNode);
			Element returnElt = outDocument.createElement("return");
			responseEltNode.appendChild(returnElt);
			returnElt.appendChild(outDocument.createTextNode(String.valueOf(res)));
		
			
			List<Source> outElements = new ArrayList<Source>();
            outElements.add(new DOMSource(outDocument.getDocumentElement()));
            // set the payload header with null
            CxfPayload<SoapHeader> responsePayload = new CxfPayload<SoapHeader>(null, outElements, null);
            exchange.getOut().setBody(responsePayload); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
 
	
	
}
