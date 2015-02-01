package tp.test.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import tp.rmi.service.RemoteCalculateur;

public class CalculateurRmiClientApp {

	public static void main(String[] args) {
		try {
			System.out.println("Getting registry");
			Registry registry = LocateRegistry.getRegistry("localhost", 12345);

			System.out.println("Lookup service");
			RemoteCalculateur calc = (RemoteCalculateur) registry.lookup("remoteCalculateurServiceBean");

			System.out.println("Invoking RMI ...");
			Double  res = calc.addition(5.2, 3.3);

			System.out.println("5.2+3.3="+res);
		} catch (Exception e) {
				e.printStackTrace();
		} 

	}

}
