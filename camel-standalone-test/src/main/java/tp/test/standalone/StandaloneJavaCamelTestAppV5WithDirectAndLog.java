package tp.test.standalone;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.main.Main;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spi.DataFormat;




public class StandaloneJavaCamelTestAppV5WithDirectAndLog {
	

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
			    	
			    	
			    
			        from("file:data/inXml")
			        .log("xml data from file (of className=${body.class.name}) : ${body}")
			     //   .convertBodyTo(String.class)
			     //   .log("xml data as String (of className=${body.class.name}) : ${body}")
			        .unmarshal(jaxbDataFormat)
			        .log("pojo/demande (of className=${body.class.name}) with id=${body.id} and type=${body.type}")
			        .to("direct:data_as_pojo");
			        
			        
			        from("direct:data_as_pojo")
			        .marshal().json(JsonLibrary.Jackson)
			        .log("data as json : ${body}")
			        .to("direct:data_as_json");
			        
			        
			        from("direct:data_as_json")
			        .setHeader(Exchange.FILE_NAME , simple ("${file:name.noext}.json"))	
			        .to("file:data/outJson"); //json_file
			        
			        
			        /*
			        from("direct:data_as_json")
			        .unmarshal().json(JsonLibrary.Jackson,tp.data.Demande.class)
			        .to("direct:data_as_pojo2");
			        
			        from("direct:data_as_pojo2")
			        .marshal(jaxbDataFormat)
			        .log("data as xml : ${body}")
			        .setHeader(Exchange.FILE_NAME , simple ("${file:name.noext}_xy.xml"))			    
			        .to("file:data/outXml"); //xml file
			         */
			    
			    }
			
			
			};
	}

}
