package blomstermala;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Controller {
	private MainUI ui;
	private BookingUI bookingUi;
	private Tur tur;
	private Booking booking;
	private ArrayList<Tur> turList = new ArrayList<Tur>();
	private ArrayList<Booking> bookList = new ArrayList<Booking>();
	
	public Controller(MainUI ui) {
		this.ui = ui;
		ui.setController(this);
		loadingDriver();
		queryTur();
	}
	
	public void loadingDriver(){
		try {
		    System.out.println("Loading driver...");
		    Class.forName("com.mysql.jdbc.Driver");
		    System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
		    throw new RuntimeException("Cannot find the driver in the classpath!", e);
		}
		
	}
	
	public String getDate(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String str = sdf.format(date);
		return str;
	}
	
	/**
	 * Hämtar Tur och fyller JList, sker vid varje uppdatering av system
	 */
	public void queryTur(){
		//String url = "jdbc:mysql://195.178.235.60:3306/m10p2249";
		String url = "jdbc:mysql://127.0.0.1:3306/dbtest";
		String username = "root";
		//String password = "elevm10p";
		String password = "";
		Connection connection = null;
		
		try {
		    connection = DriverManager.getConnection(url, username, password);
		    Statement stmt = connection.createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT Trip.TurID, Trip.Pris, Trip.Avgångstid, Trip.Ankomstid, Trip.Platser, Trip.Dag, Trip.StartStad, City.Namn, City.Land, City.Valuta, City.Språk FROM Trip INNER JOIN City ON Trip.SlutStad=City.Namn");
		    // Loopar igenom tabeller
		    while(rs.next()){
		    	int id = rs.getInt("TurID");
		    	String price = rs.getString("Pris");
		    	String avgangstid = rs.getString("Avgångstid");
		    	String ankomstid = rs.getString("Ankomstid");
		    	String startStad = rs.getString("StartStad");
		    	int seats = rs.getInt("Platser");
		    	String day = rs.getString("Dag");
		    	String cityname = rs.getString("Namn");
		    	String country = rs.getString("Land");
		    	String valuta = rs.getString("Valuta");
		    	String sprak = rs.getString("Språk");
		    	
		    	tur = new Tur(id, price, avgangstid, ankomstid, cityname, country, valuta, sprak, day, seats, startStad);
		    	turList.add(tur);
		    }
		    ui.updateList(turList);
		    turList.clear();
		    
		    stmt.close();
		    connection.close();

		} catch (SQLException e) {
		    throw new RuntimeException("Cannot connect the database!", e);
		} 
	}
	
	public void showCity(String city){
		String url = "jdbc:mysql://195.178.235.60:3306/m10p2249";
		String username = "m10p2249";
		String password = "elevm10p";
		Connection connection = null;
		
		try {
		    connection = DriverManager.getConnection(url, username, password);
		    Statement stmt = connection.createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT * FROM City WHERE Namn='"+city+"'");
		    // Loopar igenom tabeller
		    while(rs.next()){
		    	String country = rs.getString("Land");
		    	String cityName = rs.getString("Namn");
		    	String valuta = rs.getString("Valuta");
		    	String sprak = rs.getString("Språk");
		    	ui.showCityInfo(country, cityName, valuta, sprak);
		    }
		    
		    stmt.close();
		    connection.close();

		} catch (SQLException e) {
		    throw new RuntimeException("Cannot connect the database!", e);
		} 
	}
	
	public void addResenar(Resenar person){
		String url = "jdbc:mysql://195.178.235.60:3306/m10p2249";
		String username = "m10p2249";
		String password = "elevm10p";
		Connection connection = null;
		
		try {
		    System.out.println("Lägger till ny resenär!");
			connection = DriverManager.getConnection(url, username, password);
		    Statement stmt = connection.createStatement();
		    String id = person.getPersonNbr();
		    String name = person.getName();
		    String adress = person.getAdress();
		    String telephone = person.getTelephone();
		    stmt.executeUpdate("INSERT INTO `Person`(Namn,Adress,Telefon,PersonNr) VALUE ('"+name+"','"+adress+"','"+telephone+"',"+id+")");
		    stmt.close();
		    connection.close();
		    ui.userLoggedIn();
		} catch (SQLException e) {
		    throw new RuntimeException("Cannot connect the database!", e);
		} 
	}
	
	public void checkResenar(Resenar r){
		String url = "jdbc:mysql://195.178.235.60:3306/m10p2249";
		String username = "m10p2249";
		String password = "elevm10p";
		Connection connection = null;
		
		try {
			String personNr = r.getPersonNbr();
		    connection = DriverManager.getConnection(url, username, password);
		    Statement stmt = connection.createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT Person.Namn, Person.PersonNr FROM Person WHERE PersonNr="+personNr);
		    // Loopar igenom tabeller
		    if(!rs.next()){
		    	addResenar(r);
		    }else{
		    	ui.userLoggedIn();
		    	ui.welcomeBack(rs.getString("Namn"));
		    }
		    stmt.close();
		    connection.close();
		    
		} catch (SQLException e) {
		    throw new RuntimeException("Cannot connect the database!", e);
		} 
	    
	}
	
	public void addBooking(Tur t, String personNr){
		String url = "jdbc:mysql://195.178.235.60:3306/m10p2249";
		String username = "m10p2249";
		String password = "elevm10p";
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(url, username, password);
		    Statement stmt = connection.createStatement();
		    int turID = t.getId();
		    String date = getDate();
		    stmt.executeUpdate("INSERT INTO `Booking`(TurID,PersonNr,Datum) VALUE ("+turID+",'"+personNr+"','"+date+"')");
		    stmt.close();
		    connection.close();
		    updateSeats(t);
		} catch (SQLException e) {
		    throw new RuntimeException("Cannot connect the database!", e);
		} 
	}
	
	public void updateSeats(Tur t){
		String url = "jdbc:mysql://195.178.235.60:3306/m10p2249";
		String username = "m10p2249";
		String password = "elevm10p";
		Connection connection = null;
		
		try {
		    System.out.println("Uppdaterar stolar!");
			connection = DriverManager.getConnection(url, username, password);
		    Statement stmt = connection.createStatement();
		    int turID = t.getId();
		    int seats = t.getSeats();
		    seats--;
		    stmt.executeUpdate("UPDATE Trip SET Platser="+seats+" "+"WHERE TurID="+turID);
		    stmt.close();
		    connection.close();
		    queryTur();
		} catch (SQLException e) {
		    throw new RuntimeException("Cannot connect the database!", e);
		} 
	}
	
	public void queryBooking(String personNbr){
		String url = "jdbc:mysql://195.178.235.60:3306/m10p2249";
		String username = "m10p2249";
		String password = "elevm10p";
		Connection connection = null;
		
		try {
		    connection = DriverManager.getConnection(url, username, password);
		    Statement stmt = connection.createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT Trip.StartStad, Trip.SlutStad, Booking.Datum, Person.Namn FROM Booking INNER JOIN Person INNER JOIN Trip ON Booking.TurID=Trip.TurID  WHERE Booking.PersonNr=Person.PersonNr AND Person.PersonNr='"+personNbr+"'");
		    // Loopar igenom tabeller
		    while(rs.next()){
		    	String startStad = rs.getString("StartStad");
		    	String slutStad = rs.getString("SlutStad");
		    	String datum = rs.getString("Datum");
		    	String name = rs.getString("Namn");
		    	
		    	booking = new Booking(startStad, slutStad, datum, name);
		    	bookList.add(booking);
		    }
		    ui.updateBooking(bookList);
		    bookList.clear();
		    
		    stmt.close();
		    connection.close();

		} catch (SQLException e) {
		    throw new RuntimeException("Cannot connect the database!", e);
		} 
	}
	

}
