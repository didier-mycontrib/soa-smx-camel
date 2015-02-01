package tp.camel.bean;

public class HelloWorldBean {
	
	public void sayHelloInOnly(String name){
		System.out.println("Hello "+ name);
	}
	
	public String sayHelloInOut(String name){
		return "Hello " + name;
	}

}
