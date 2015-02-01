package tp.test.standalone;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.main.Main;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spi.DataFormat;

public class StandaloneJavaCamelTestAppV4WithTransform {
	

	public static void main(String[] args) {
		
	    // create an instance of predefined "org.apache.camel.main.Main" (or a subclass):
		Main camelMain = new Main();
		camelMain.enableHangupSupport(); //for stopping when Ctrl+c
		//camelMain.bind("processByBeanZz", new ProcessorBeanZz());
	    // configure route:
		camelMain.addRouteBuilder(createRouteBuilder());
		try {
			//start camel App and wainting Ctrl+c to stop:
			System.out.println("camel is running , waiting for ctrl+c to stop ");
			camelMain.run(args);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * dependances "maven" necessaires: camel-jaxb , camel-jackson
	 */
		
	public static RouteBuilder createRouteBuilder(){
		return new /* inner anonymous class which implements */ RouteBuilder() {
			
			
			    public void configure() {
			    	DataFormat jaxbDataFormat = new JaxbDataFormat("tp.data");
			    	//DataFormat jaxbDataFormat=null;
					//try {
						//jaxbDataFormat = new JaxbDataFormat(JAXBContext.newInstance(tp.data.Demande.class));
					//} catch (JAXBException e) {
						//e.printStackTrace();
					//}
			    	
			        from("file:data/inXml")
			        .unmarshal(jaxbDataFormat)			   
			        //.to("dataformat:jaxb:unmarshal?contextPath=tp.data")	//ok mais syntaxe moins homogene en DSL		        
			        .marshal().json(JsonLibrary.Jackson)
			        //.setHeader(Exchange.FILE_NAME, constant("fichier_xy.txt"))
			        .setHeader(Exchange.FILE_NAME , simple ("${file:name.noext}.json"))			    
			        .to("file:data/outJson"); 
			        //.to("file:///home/formation/Bureau/tp/temp/data/outJson"); 
			    
			    }
			
			/* 
			public void configure() {
				XmlJsonDataFormat xmlJsonDataFormat = new XmlJsonDataFormat();
				//xmlJsonDataFormat.setEncoding("UTF-8");
				//xmlJsonDataFormat.setForceTopLevelObject(true);
				//xmlJsonDataFormat.setTrimSpaces(true);
				//xmlJsonDataFormat.setRootName("newRoot");
				xmlJsonDataFormat.setSkipNamespaces(true);
				xmlJsonDataFormat.setRemoveNamespacePrefixes(true);
				//xmlJsonDataFormat.setExpandableProperties(Arrays.asList("d", "e"));
		    	
		        from("file:data/inXml")
		        .marshal(xmlJsonDataFormat)   //NB camel-xmljson enable direct XML --> JSON with marshal(xmlJsonDataFormat) or .marshal().xmljson()
		                                                        // and direct JSON --> XML with unmarshal(xmlJsonDataFormat) or .unmarshal().xmljson()
		        .setHeader(Exchange.FILE_NAME , simple ("${file:name.noext}.json"))			    
		        .to("file:data/outJson"); 
		    
		    } */
			};
	}

}
