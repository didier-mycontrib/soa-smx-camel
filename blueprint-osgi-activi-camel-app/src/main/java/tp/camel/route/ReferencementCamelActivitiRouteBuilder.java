package tp.camel.route;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.xmljson.XmlJsonDataFormat;
import org.apache.camel.language.Simple;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * dans environnement d'execution servicemix/osgi , le code de cette classe nécessite:
 * 
 * features:install  camel-xmljson
 * features:install  camel-jackson
 * features:install activiti
 */


public class ReferencementCamelActivitiRouteBuilder extends RouteBuilder {
	
	private final Helper helper = new Helper();
	

		public void configure() throws Exception {
		
		XmlJsonDataFormat xmlJsonFormatReferencement = new XmlJsonDataFormat();
		xmlJsonFormatReferencement.setEncoding("UTF-8");
		xmlJsonFormatReferencement.setRootName("referencement");//for xml from json
		
		XmlJsonDataFormat xmlJsonFormatVerifSiret = new XmlJsonDataFormat();
		xmlJsonFormatVerifSiret.setEncoding("UTF-8");
		xmlJsonFormatVerifSiret.setRootName("verifSiret");//for xml from json
	       
				
	        from("file:/home/formation/Bureau/tp/var/inRef")
	        .marshal(xmlJsonFormatReferencement) // convert xml to json
	            .setBody(method(helper,"getProcessVariablesForReferencement"))
	            .setProperty("PROCESS_KEY_PROPERTY", simple("${body['referencement']['siret']}"))
	            .log("sending body to activiti:processReferencement : ${body} " )
	           .to("activiti:processReferencement") //startEvent of "processReferencement" with or without message
	           .log("processReferencement started" );
	        
	        
	        from("activiti:processReferencement:servicetaskSendVerifSiret?copyVariablesToBodyAsMap=true")
	        .log("Sending VerifSiret from activi process: ${body}")
	        .setBody(simple("${body['verifSiret']}"))
	         .log("Sending extracted VerifSiret from activi process: ${body}")
	        .setHeader("CamelFileName", simple("verifSiretOut${body['siret']}.xml"))
	        .setBody(method(helper,"getJsonStringFromBodyJavaMap"))
	        .unmarshal(xmlJsonFormatVerifSiret) // convert json to xml
	        .to("file:/home/formation/Bureau/tp/var/outVerifSiret");
	        
	        from("file:/home/formation/Bureau/tp/var/inVerifSiret")
	         .marshal(xmlJsonFormatVerifSiret) // convert xml to json
            .setBody(method(helper,"getProcessVariablesForVerifSiret"))
            .setProperty("PROCESS_KEY_PROPERTY", simple("${body['verifSiret']['siret']}"))
             .log("sending body to activiti:processReferencement:receivetaskVerifSiret : ${body} " )
           .to("activiti:processReferencement:receivetaskVerifSiret") 
            .log("Process to handle incoming result of VerifSiret has been notified" );
	        
	        from("activiti:processReferencement:servicetaskSendNotifRef?copyVariablesToBodyAsMap=true")
	        .log("sending notification from activi process: ${body}")
	        .setBody(simple("${body['referencement']}"))
	        .setHeader("CamelFileName", simple("notificationReferencement${body['siret']}.xml"))
	        .setBody(method(helper,"getJsonStringFromBodyJavaMap"))
	        .unmarshal(xmlJsonFormatReferencement) // convert json to xml
	        .to("file:/home/formation/Bureau/tp/var/outNotif");
	     
	     
		
	}
	
	/*
     * A few helper methods used for routing
     */
    public static final class Helper {
    	
    	private Map<String,String> convertJsonStringToJavaMap(String jsonString){
    	Map<String,String> javaMap = null; 
    	ObjectMapper jsonMapper = new ObjectMapper();
    	      	
    	try {
    		javaMap = jsonMapper.readValue(jsonString, new TypeReference<HashMap<String,String>>(){});
		} catch (Exception e) {
				//e.printStackTrace();
			javaMap = new HashMap<String,String>();
			javaMap.put("execption", e.getMessage());
		}
    	return javaMap;
    	}
    	
    	private String convertJavaMapToJsonString(Map javaMap){
        	String jsonString = "";
        	ObjectMapper jsonMapper = new ObjectMapper();
        	      	
        	try {
        		jsonString = jsonMapper.writeValueAsString(javaMap);
    		} catch (Exception e) {
    				//e.printStackTrace();
    			jsonString = "{ 'exception' : '"+  e.getMessage() +"' }";
    		}
        	return jsonString;
        	}
		
    	 @Handler
    	public String getJsonStringFromBodyJavaMap(@Body Map javaMap){
    		return convertJavaMapToJsonString(javaMap);
    	}

       
        @Handler
        public Map getProcessVariablesForReferencement(@Body String body,
                                       @Simple("${date:now:yyyy-MM-dd kk:mm:ss}") String timestamp) {
            Map<String, Object> variables = new HashMap<String, Object>();
            //variables.put("messageReferencement", body);
            Map<String, String> referencement = null;
            referencement=convertJsonStringToJavaMap(body);
           
            variables.put("referencement", referencement);
            variables.put("timestamp", timestamp);
            return variables;
        }
        
        @Handler
        public Map getProcessVariablesForVerifSiret(@Body String body,
                                       @Simple("${date:now:yyyy-MM-dd kk:mm:ss}") String timestamp) {
            Map<String, Object> variables = new HashMap<String, Object>();
            //variables.put("messageVerifSiret", body);
            Map<String, String> verifSiret = null;
            verifSiret=convertJsonStringToJavaMap(body);
            
            variables.put("verifSiret", verifSiret);
           // variables.put("timestamp", timestamp);
            return variables;
        }
        
    }

}
