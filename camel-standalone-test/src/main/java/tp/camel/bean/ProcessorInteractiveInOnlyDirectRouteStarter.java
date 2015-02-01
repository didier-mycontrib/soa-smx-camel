package tp.camel.bean;

import javax.swing.JOptionPane;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessorInteractiveInOnlyDirectRouteStarter implements Processor {
	
	public static Logger log = LoggerFactory.getLogger(ProcessorInteractiveInOnlyDirectRouteStarter.class);
	
    public void process(Exchange exchange) {
    	String msg = JOptionPane.showInputDialog(null, "text of body to send to direct:startInOnly");
    	if(msg!=null){
        exchange.getContext().createProducerTemplate().sendBody("direct:startInOnly",ExchangePattern.InOnly, msg);
    	}
	}

    
}
