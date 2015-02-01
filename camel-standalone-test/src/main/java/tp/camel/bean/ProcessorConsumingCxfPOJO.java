package tp.camel.bean;

import org.apache.camel.Exchange;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.cxf.message.MessageContentsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessorConsumingCxfPOJO {
	
	public static Logger log = LoggerFactory.getLogger(ProcessorConsumingCxfPOJO.class);
	
	/*
    public void processExchange(Exchange exchange) {
        log.info("message processed by ProcessorConsumingCxfPOJO{}", exchange);
       
        String opName = (String) exchange.getIn().getHeader(CxfConstants.OPERATION_NAME);
        System.out.println(CxfConstants.OPERATION_NAME+":"+opName);
        
        // Get the parameters list which element is the holder.
        MessageContentsList msgList = (MessageContentsList)exchange.getIn().getBody();
        for(Object obj : msgList){
        	System.out.println("param : " + obj.toString());
        }
        Double a = (Double) msgList.get(0);
        Double b = (Double) msgList.get(1);
        
        log.info("sending response");
        Object[] result = new Object[1];
        result[0]=new Double(a+b);
        exchange.getOut().setBody(result);
    }
    
    public void processBody(String body) {
        log.info("body processed by ProcessorBeanZz {}", body);
    }
	*/
	//public Object[] addition(MessageContentsList param) OK
	//public Object[] addition(MessageContentsList params,Exchange exchange)OK
	public Object[] addition(Object[] params/*,Exchange exchange*/){
		/*
		 String opName = (String) exchange.getIn().getHeader(CxfConstants.OPERATION_NAME);
	        System.out.println(CxfConstants.OPERATION_NAME+":"+opName);
	    */    
		Double a= (Double) params[0];//params.get(0);
		Double b= (Double) params[1];//params.get(1);
		System.out.println("a="+a+",b="+b);
		Object[] resTab = new Object[1];
		Double res = a+b;
		System.out.println("a+b="+res);
		resTab[0]=res;
		return resTab;
	
	}
	
	
}
