package tp.test.standalone;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class StandaloneJavaCamelTestApp {

	public static void main(String[] args) {
		CamelContext camelContext = new DefaultCamelContext();
		try {
			camelContext.addRoutes(new /* inner anonymous class which implements */ RouteBuilder() {
			    public void configure() {
			        from("file:data/in").to("file:data/outDefault"); // Camel DSL Syntax
			    }
			});
			camelContext.start();
			Thread.sleep(2000);//au moins 2000ms
		    camelContext.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
