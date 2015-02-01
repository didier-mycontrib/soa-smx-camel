package tp.camel.bean;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.bean.BeanInvocation;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.camel.impl.DefaultExchange;
import org.apache.cxf.message.MessageContentsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProcessorBeanInvocationCallingCxfPojo  {	
	
	private String camelCxfPojoUri ; //propriété à fixer dans config spring
	

	public String getCamelCxfPojoUri() {
		return camelCxfPojoUri;
	}

	public void setCamelCxfPojoUri(String camelCxfPojoUri) {
		this.camelCxfPojoUri = camelCxfPojoUri;
	}

	public static Logger log = LoggerFactory.getLogger(ProcessorBeanInvocationCallingCxfPojo.class);
	
	 public void processExchange(Exchange biExchange){		 
		 try {
			 CamelContext camelContext = biExchange.getContext(); 
			 ProducerTemplate  template = camelContext.createProducerTemplate();
			 
			 BeanInvocation bi = (BeanInvocation) biExchange.getIn().getBody();
			 
			 Exchange cxfPojoRequestExchange = new DefaultExchange(camelContext, ExchangePattern.InOut);
			 MessageContentsList msgList= new MessageContentsList();
			 for(Object param : bi.getArgs()){
					msgList.add(param);
				}
			 cxfPojoRequestExchange.getIn().setBody(msgList);
			 cxfPojoRequestExchange.getIn().setHeader(CxfConstants.OPERATION_NAME, bi.getMethod().getName());
			 log.info("sended cxfPojo request : " + cxfPojoRequestExchange.getIn()); 
			 Exchange cxfPojoResponseExchange = template.send(camelCxfPojoUri, cxfPojoRequestExchange);
			 org.apache.camel.Message out = cxfPojoResponseExchange.getOut();
			 log.info("Received cxfPojo response : " + out);
			 MessageContentsList result = (MessageContentsList)out.getBody();
			 Object javaResult=result.get(0);
			 
			 biExchange.getOut().setBody(javaResult);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		 
	 }

	
}
