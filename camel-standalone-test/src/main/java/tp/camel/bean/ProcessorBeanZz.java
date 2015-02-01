package tp.camel.bean;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessorBeanZz {
	
	public static Logger log = LoggerFactory.getLogger(ProcessorBeanZz.class);
	
    public void processExchange(Exchange msg) {
        log.info("message processed by ProcessorBeanZz{}", msg);
    }
    
    public void processBody(String body) {
        log.info("body processed by ProcessorBeanZz {}", body);
    }
}
