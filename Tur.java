package blomstermala;

public class Tur {
	private int turID;
	private String price, departureTime, arrivalTime, cityname, country, currency, language, day, depCity;
	private int seats;
	
	public Tur(int turID, String price, String avgangstid, String ankomstid, String cityname, String country, String valuta, String sprak, String day, int seats, String startStad) {
		this.turID = turID;
		this.price = price;
		this.departureTime = avgangstid;
		this.arrivalTime = ankomstid;
		this.cityname = cityname;
		this.country = country;
		this.currency = valuta;
		this.language = sprak;
		this.day = day;
		this.seats = seats;
		this.depCity = startStad;
	}
	
	/**
	 * toString() method returns the string shown in the JList/table of the 
	 * main UI
	 */
	public String toString(){
		String res = day+" "+departureTime+"-"+arrivalTime+" "+depCity+"-"+cityname+" "+"ANTAL PLATSER:"+seats;
		return res;
	}
	

	
	public int getId(){
		return turID;
	}
	
	public int getSeats(){
		return seats;
	}
	
	public String getCity(){
		return cityname;
	}

}
