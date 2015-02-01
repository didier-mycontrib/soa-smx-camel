package tp.service;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface Calculateur {

	public double addition(@WebParam(name="a")double a,
			               @WebParam(name="b")double b);
	public double soustraction(@WebParam(name="a")double a,
            @WebParam(name="b")double b);
	public double multiplication(@WebParam(name="a")double a,
			                     @WebParam(name="b")double b);
	public double division(@WebParam(name="a")double a,
            @WebParam(name="b")double b);
}
