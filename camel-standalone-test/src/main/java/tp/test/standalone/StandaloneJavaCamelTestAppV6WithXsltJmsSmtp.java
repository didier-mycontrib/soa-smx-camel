package tp.test.standalone;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.builder.ExpressionBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.Namespaces;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.main.Main;
import org.apache.camel.spi.DataFormat;




public class StandaloneJavaCamelTestAppV6WithXsltJmsSmtp {
	

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
			    	
			    	org.apache.camel.builder.xml.Namespaces ns = new Namespaces("tp", "http://data.tp/")
		        	                                             .add("std", "http://standard/");
			    	
			    	getContext().addComponent("activemq", 	ActiveMQComponent.activeMQComponent("tcp://127.0.0.1:61616"));
			    
			        from("file:data/inXml")
			        .log("xml data from file (of className=${body.class.name}) : ${body}")
			     //   .convertBodyTo(String.class)
			     //   .log("xml data as String (of className=${body.class.name}) : ${body}")
			         .to("xslt:xsl/demande_order.xslt")  
			        .log("data converted by xslt (of className=${body.class.name}) : ${body}")
			        
			        .to("file:data/outXml"); //xml file
			        
			        //.to("activemq:queue:queue.A") ; //ok
			        
			        /*
			        //.setHeader("subject", constant("res xslt"))	
			        
			        //.setHeader("subject", ExpressionBuilder.append(constant("xslt res"), ns.xpath("/std:order/std:id", String.class)))
			        .setProperty("order_id", ns.xpath("/std:order/std:id", String.class)) //or .setHeader()
			        .setHeader("subject",simple("xslt res ${property[order_id]}"))
			        
			        .setHeader("to",simple("user${property[order_id]}@localhost"))
			        //.setHeader("to",constant("user1@localhost"))
			        
			        .setHeader("from",simple("user${property[order_id]}@localhost"))
			        //.to("smtp://localhost:25?username=root&password=root&to=user1@localhost&from=user2@localhost");
			        .to("smtp://localhost:25?username=root&password=root");
			         */
			    
			    }
			
			
			};
	}

}
