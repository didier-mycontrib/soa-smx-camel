package tp.test.standalone;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;

import tp.camel.bean.ProcessorBeanXyV3;
import tp.camel.bean.ProcessorBeanZz;

public class StandaloneJavaCamelTestAppV3WithBean {
	

	public static void main(String[] args) {
		
	    // create an instance of predefined "org.apache.camel.main.Main" (or a subclass):
		Main camelMain = new Main();
		camelMain.enableHangupSupport(); //for stopping when Ctrl+c
		camelMain.bind("processByBeanXy", new ProcessorBeanXyV3());
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
		
	public static RouteBuilder createRouteBuilder(){
		return new /* inner anonymous class which implements */ RouteBuilder() {
			    public void configure() {
			        from("file:data/in")
			        //.to("bean:processByBeanXy")
    			    .beanRef("processByBeanXy")
			        .bean(ProcessorBeanZz.class, "processBody")
			        //.to("bean:processByBeanZz?method=processBody")
			        .to("file:data/outDefault"); // Camel DSL Syntax
			    }
			};
	}

}
