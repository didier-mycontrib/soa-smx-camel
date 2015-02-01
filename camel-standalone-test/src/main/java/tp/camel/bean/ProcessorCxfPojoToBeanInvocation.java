package tp.camel.bean;

import java.lang.reflect.Method;

import org.apache.camel.Exchange;
import org.apache.camel.component.bean.BeanInvocation;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.cxf.message.MessageContentsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessorCxfPojoToBeanInvocation {
	
	public static Logger log = LoggerFactory.getLogger(ProcessorCxfPojoToBeanInvocation.class);
	
	
    public BeanInvocation processExchange(Exchange exchange) {
    	BeanInvocation bi=null;
        try {
			log.info("message processed by ProcessorCxfPojoToBeanInvocation{}", exchange);
      
			String opName = (String) exchange.getIn().getHeader(CxfConstants.OPERATION_NAME);
			System.out.println("in ProcessorCxfPojoToBeanInvocation " + CxfConstants.OPERATION_NAME+":"+opName);
			
			// Get the parameters list which element is the holder.
			MessageContentsList msgList = (MessageContentsList)exchange.getIn().getBody();
			int nbParams= msgList.size();
			String nextBeanInvocationInterface = (String) exchange.getIn().getHeader("nextBeanInvocationInterface");
			Class itfAsClass = Class.forName(nextBeanInvocationInterface);
			Class parameterTypes[] = new Class[nbParams];
			for(int i=0;i<nbParams;i++){
				parameterTypes[i]=msgList.get(i).getClass();
			}
            Method opAsMethod = null;
            try {
				opAsMethod= itfAsClass.getMethod(opName, parameterTypes);
			} catch (NoSuchMethodException nsme) {
				for(Method m : itfAsClass.getMethods()){
					if(m.getName().equals(opName)){
						opAsMethod=m; break;
					}
				}
			}
        
			bi = new BeanInvocation(opAsMethod ,msgList.toArray());
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
        return bi;            
    }
    
 
	
}
