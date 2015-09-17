package tp.camel.route;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.xmljson.XmlJsonDataFormat;
import org.apache.camel.language.Simple;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyCamelRouteBuilder extends RouteBuilder  {

	private final Helper helper = new Helper();

	@Override
	public void configure() throws Exception {
		
		XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();
		xmlJsonFormat.setEncoding("UTF-8");
		//xmlJsonFormat.setForceTopLevelObject(true);
		//xmlJsonFormat.setTrimSpaces(true);
		//xmlJsonFormat.setRootName("newRoot");
		//xmlJsonFormat.setSkipNamespaces(true);
		//xmlJsonFormat.setRemoveNamespacePrefixes(true);
		
	       
	        from("file:data/inXml")
	        .log("data (of className=${body.class.name}) as xml: ${body}")
	        .marshal(xmlJsonFormat) // convert xml to json
	        .log("data (of className=${body.class.name}) as json: ${body}")
	        .setHeader("CamelFileName", simple("${file:name.noext}.json"))
	        .to("file:data/outJson")
	       // .convertBodyTo(String.class)
	         .setBody(method(helper))
	         .log("data (of className=${body.class.name}) as json: ${body}");
	        
	        
	        
           
		
	}
	
	/*
     * A few helper methods used for routing
     */
    public static final class Helper {

        /*
         * This method will extract information from the Exchange (using Camel annotations) and put them in a
         * Map that will be used for setting up the process' variables.
         */
        @Handler
        public Map getProcessVariables(@Body String body,
                                       @Header(Exchange.FILE_NAME) String filename,
                                       @Simple("${date:now:yyyy-MM-dd kk:mm:ss}") String timestamp) {
        	
        	ObjectMapper jsonMapper = new ObjectMapper();
        	Map<String,String> subMap = null;        	
        	try {
				subMap = jsonMapper.readValue(body, new TypeReference<HashMap<String,String>>(){});
			} catch (Exception e) {
					//e.printStackTrace();
					subMap = new HashMap<String,String>();
					subMap.put("execption", e.getMessage());
			}
			
        	
            Map<String, Object> variables = new HashMap<String, Object>();
            
            variables.put("subMap", body);
            variables.put("messageId", filename);
            variables.put("timestamp", timestamp);
            return variables;
        }
    }
	

}
