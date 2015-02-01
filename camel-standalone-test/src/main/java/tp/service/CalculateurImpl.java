package tp.service;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(endpointInterface="tp.service.Calculateur")
public class CalculateurImpl implements Calculateur {

	@Override
	public double addition(double a,double b) {
		return a+b;
	}
	
	@Override
	public double soustraction(double a,double b) {
		return a-b;
	}

	@Override
	public double multiplication(double a,double b) {
		return a*b;
	}

	@Override
	public double division(double a,double b) {
		return a/b;
	}

}
