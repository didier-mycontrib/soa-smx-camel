package tp.camel.converter;

import java.lang.reflect.Method;

import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.component.bean.BeanInvocation;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.cxf.message.MessageContentsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Converter
public class CxfPojoToBeanInvocationConverter  {	
	
	public static Logger log = LoggerFactory.getLogger(CxfPojoToBeanInvocationConverter.class);
	
	@Converter
	public static BeanInvocation cxfPojoMessageContentsListToBeanInvocation(MessageContentsList msgList, Exchange exchange) {
		BeanInvocation bi=null;
        try {    
		String opName = (String) exchange.getIn().getHeader(CxfConstants.OPERATION_NAME);
		log.info("in CxfPojoToBeanInvocationConverter " + CxfConstants.OPERATION_NAME+":"+opName);
		
		//MessageContentsList msgList = (MessageContentsList) exchange.getIn().getBody();
		int nbParams= msgList.size();
		String nextBeanInvocationInterface = (String) exchange.getIn().getHeader("nextBeanInvocationInterface");
		log.info("in CxfPojoToBeanInvocationConverter nextBeanInvocationInterface=" + nextBeanInvocationInterface);
		Class itfAsClass = Class.forName(nextBeanInvocationInterface);
		Class parameterTypes[] = new Class[nbParams];
		for(int i=0;i<nbParams;i++){
			parameterTypes[i]=msgList.get(i).getClass();
		}
        Method opAsMethod = null;
        try {
			opAsMethod= itfAsClass.getMethod(opName, parameterTypes);
		} catch (NoSuchMethodException nsme) {
			//aternative Method selection with aproximated types (ex: "double" instead "Double):
			for(Method m : itfAsClass.getMethods()){
				if(m.getName().equals(opName)){
					opAsMethod=m; break;
				}
			}
		}
		bi = new BeanInvocation(opAsMethod ,msgList.toArray());
		log.info("in CxfPojoToBeanInvocationConverter , builded BeanInvocation = " +bi.toString());
	} catch (Exception e) {
		log.error("CxfPojoToBeanInvocationConverter failed :" , e);
	} 
    return  bi; 
	}
}
