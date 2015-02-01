package tp.test.standalone;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.Namespaces;
import org.apache.camel.main.Main;

public class StandaloneJavaCamelTestAppV2 {
	

	public static void main(String[] args) {
		
	    // create an instance of predefined "org.apache.camel.main.Main" (or a subclass):
		Main camelMain = new Main();
		camelMain.enableHangupSupport(); //for stopping when Ctrl+c
	    // configure route:
		camelMain.addRouteBuilder(createRouteBuilder());
		try {
			//start camel App and wainting Ctrl+c to stop:
			camelMain.run(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public static RouteBuilder createRouteBuilder(){
		return new /* inner anonymous class which implements */ RouteBuilder() {
			    public void configure() {
			    	
			    	Namespaces ns = new Namespaces("tp", "http://data.tp/")
			        				.add("xsd", "http://www.w3.org/2001/XMLSchema");
			    	
			     //   from("file:data/in").to("file:data/outDefault"); // Camel DSL Syntax
			    	
			    	from("file:data/in")
			    	.choice()
			    	    .when().xpath("/tp:demande/tp:type = 'A'",ns)
			    	        .to("file:data/outA")
			    		.when().xpath("/tp:demande/tp:type = 'B'",ns)
			    			.to("file:data/outB")
			    		.otherwise()
			    			.to("file:data/outDefault")
			    	.endChoice();
			    }
			};
	}

}
