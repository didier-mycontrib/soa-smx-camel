package tp.camel.bean;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessorBeanXy implements Processor {
	
	public static Logger log = LoggerFactory.getLogger(ProcessorBeanXy.class);
	
    public void process(Exchange msg) {
        log.info("message processed by ProcessorBeanXy {}", msg);
    }
}
