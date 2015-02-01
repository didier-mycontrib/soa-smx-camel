package tp.service;

import javax.jws.WebParam;
import javax.jws.WebService;

//interface du service web (pris en charge par tomcat+spring+cxf) externe à ServiceMix
// avec annotations (@WebService , ....) indispensables 
@WebService
public interface Calculateur {
	public double addition(@WebParam(name="a")double a,@WebParam(name="b")double b);
	public double multiplication(@WebParam(name="a")double a,@WebParam(name="b")double b);	
	public String getAuteur();
}
