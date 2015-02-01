package tp.camel.bean;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;

import tp.data.MyLogStruct;

/*
 * V3 = V2 with use of ProducerTemplate (for less line of code)
 */

public class ProcessorBeanXyV3 implements Processor {
	
	
    public void process(Exchange msgExchange) {
    	try{
        //init camelContext from messageExchange:
    	CamelContext camelContext = msgExchange.getContext(); 
    	// create ProducerTemplate:
    	ProducerTemplate  template = camelContext.createProducerTemplate();
      
    	// set the in message payload (=body) with the body part of msgExchange parameter
        String msgBody = msgExchange.getIn().getBody(String.class);
        
        //v1 : 
        //String bodyForLogMsg  = msgExchange.toString() + " --- " +msgBody;
        //v2 : 
        
        MyLogStruct myLogStructObj = new MyLogStruct();
        myLogStructObj.setExchangeName(msgExchange.toString());
        myLogStructObj.setMessageContent(msgBody);
        myLogStructObj.setMessageName(msgExchange.getIn().getMessageId());
        
        String bodyForLogMsg =  (String) template.sendBody("velocity:velocity-template/logTemplate.vm", ExchangePattern.InOut,myLogStructObj);
       //NB : need of camel-velocity maven dependency
        //System.out.println("bodyForLogMsg:"+bodyForLogMsg);
        
        template.sendBody("log:tp.camel.xy",bodyForLogMsg  );
        //template.sendBodyAndHeader("file://target/subfolder", name, FileComponent.HEADER_FILE_NAME, filename);
	    } catch (Exception e) {
	        // we ignore any exceptions and just rethrow as runtime
	        //throw new RuntimeException(e);
	    	e.printStackTrace();
	    }
    }

	
}
