package blomstermala;

public class Resenar {
	private String personNbr;
	private String name, adress, telephone;
	
	public Resenar(String personNbr, String name, String adress, String telephone) {
		this.personNbr = personNbr;
		this.name = name;
		this.adress = adress;
		this.telephone = telephone;
	}
	
	public String getPersonNbr(){
		return personNbr;
	}
	
	public String getName(){
		return name;
	}
	
	public String getAdress(){
		return adress;
	}
	
	public String getTelephone(){
		return telephone;
	}

}
