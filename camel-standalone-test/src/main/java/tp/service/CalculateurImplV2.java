package tp.service;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(endpointInterface="tp.service.Calculateur")
public class CalculateurImplV2 implements Calculateur {

	@Override
	public double addition(double a,double b) {
		double res=a+b;
		System.out.println("addition("+a+","+b+")="+res);
		return res;
	}
	
	@Override
	public double soustraction(double a,double b) {
		double res=a-b;
		System.out.println("soustraction("+a+","+b+")="+res);
		return res;
	}

	@Override
	public double multiplication(double a,double b) {
		double res=a*b;
		System.out.println("multiplication("+a+","+b+")="+res);
		return res;
	}

	@Override
	public double division(double a,double b) {
		double res=a/b;
		System.out.println("division("+a+","+b+")="+res);
		return res;
	}

}
