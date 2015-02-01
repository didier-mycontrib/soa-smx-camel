package tp.camel.bean;

import javax.swing.JOptionPane;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessorInteractiveInOutDirectRouteStarter implements Processor {
	
	public static Logger log = LoggerFactory.getLogger(ProcessorInteractiveInOutDirectRouteStarter.class);
	
   
    public void process(Exchange exchange) {	
    	String msg = JOptionPane.showInputDialog(null, "text of body to send to direct:startInOut");
    	if(msg!=null){
    		String result = (String) exchange.getContext().createProducerTemplate().requestBody("direct:startInOut", msg);
    		System.out.println("result of InOut global exchange:" + result);
    	}
    }
    
}
