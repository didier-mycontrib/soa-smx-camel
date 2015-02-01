package tp.camel.bean;

import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Producer;

public class ProcessorBeanXyV2 implements Processor {
	
	
    public void process(Exchange msgExchange) {
    	try{
        //init camelContext from messageExchange:
    	CamelContext camelContext = msgExchange.getContext();    		
        // get the log component
        Component logComponent = camelContext.getComponent("log");
 
        // create an endpoint and configure it.
        // Notice the URI parameters this is a common pratice in Camel to configure
        // endpoints based on URI.
        // tp.camel.xy = the log category used. Will log at INFO level as default
        Endpoint logEndpoint = logComponent.createEndpoint("log:tp.camel.xy");
 
        // create an Exchange that we want to send to the endpoint
        Exchange logExchange = logEndpoint.createExchange();
        // set the in message payload (=body) with the body part of msgExchange parameter
        String msgBody = msgExchange.getIn().getBody(String.class);
        //System.out.println("msgBody:"+msgBody);
        logExchange.getIn().setBody(msgExchange.toString() + " --- " +msgBody );
 
        // now we want to send the exchange to this endpoint and we then need a producer
        // for this, so we create and start the producer.
        Producer producer = logEndpoint.createProducer();
        producer.start();
        // process the logExchange will send the logExchange to the logComponent, that will process
        // the logExchange and yes log the payload
        producer.process(logExchange);
 
        // stop the producer, we want to be nice and cleanup
        producer.stop();
 
	    } catch (Exception e) {
	        // we ignore any exceptions and just rethrow as runtime
	        //throw new RuntimeException(e);
	    	e.printStackTrace();
	    }
    }

	
}
