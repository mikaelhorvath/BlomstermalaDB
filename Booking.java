package blomstermala;

public class Booking {
	private String startStad;
	private String slutStad;
	private String datum;
	private String name;
	
	public Booking(String startStad, String slutStad, String datum, String name) {
		this.startStad = startStad;
		this.slutStad = slutStad;
		this.datum = datum;
		this.name = name;
	}
	
	public String toString(){
		return startStad+" "+"-"+" "+slutStad+" "+datum+" "+"BOKAD AV:"+" "+name;
	}

}
