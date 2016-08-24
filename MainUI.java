package blomstermala;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class MainUI extends JPanel implements ActionListener {
	private DefaultListModel<Tur> list = new DefaultListModel<Tur>();
	private ArrayList<Booking> bookList = new ArrayList<Booking>();
	private JList<Tur> tableList = new JList<Tur>(list);
	private Controller cont;
	private boolean isLoggedIn = false; 
	private String personNbr;
	private JTextField persnrField = new JTextField();
	private JTextField nameField = new JTextField();
	private JTextField adressField = new JTextField();
	private JTextField telephoneField = new JTextField();
	private JButton bookBtn = new JButton("Book Trip!");
	private JButton showBtn = new JButton("Show bookings!");
	private JButton regBtn = new JButton("Register/Login");
	private JButton showCityBtn = new JButton("Show info");
	private JLabel persNrLbl = new JLabel("Social Nr (yymmddnnnn) *");
	private JLabel nameLbl = new JLabel("Name *");
	private JLabel adressLbl = new JLabel("Adress *");
	private JLabel telLbl = new JLabel("Telephone *");
	
	public MainUI() {
		setLayout(null);
		setPreferredSize(new Dimension(500,600));
		
		updateUI();
		
		tableList.setBounds(0, 0, 500, 300);
		add(tableList);
		
		persnrField.setBounds(5, 320, 210, 30);
		add(persnrField);
		persNrLbl.setBounds(225, 325, 200, 20);
		add(persNrLbl);
		
		nameField.setBounds(5, 360, 210, 30);
		add(nameField);
		nameLbl.setBounds(225, 365, 200, 20);
		add(nameLbl);
		
		adressField.setBounds(5, 400, 210, 30);
		add(adressField);
		adressLbl.setBounds(225, 405, 200, 20);
		add(adressLbl);
		
		telephoneField.setBounds(5, 440, 210, 30);
		add(telephoneField);
		telLbl.setBounds(225, 445, 200, 20);
		add(telLbl);
		
		bookBtn.setBounds(5, 490, 150, 30);
		add(bookBtn);
		regBtn.setBounds(5, 530, 210, 30);
		add(regBtn);
		showCityBtn.setBounds(5, 560, 210, 30);
		add(showCityBtn);
		showBtn.setBounds(165, 490, 150, 30);
		add(showBtn);
		
		showBtn.addActionListener(this);
		bookBtn.addActionListener(this);
		regBtn.addActionListener(this);
		showCityBtn.addActionListener(this);
		
	}
	
	public void setController(Controller cont){
		this.cont = cont;
	}
	
	public void welcomeBack(String name){
		JOptionPane.showMessageDialog(null, "Welcome back"+" "+name+"!");
	}
	
	public void updateUI(){
		if(isLoggedIn == false){
			bookBtn.setEnabled(false);
			showBtn.setEnabled(false);
			regBtn.setEnabled(true);
			persnrField.setEditable(true);
		}else{
			bookBtn.setEnabled(true);
			showBtn.setEnabled(true);
			regBtn.setEnabled(false);
			persnrField.setEditable(false);
		}
	}
	
	public void userLoggedIn(){
		isLoggedIn = true;
		updateUI();
	}
	
	public void showCityInfo(String country, String city, String valuta, String sprak){
		String res = "Country:"+" "+country+"\n"+"City:"+" "+city+"\n"+"Currency:"+" "+valuta+"\n"+"Språk:"+" "+sprak;
		JOptionPane.showMessageDialog(null, res);
	}
	
	public void updateList(ArrayList<Tur> arrList){
		list.clear();
		for (Tur turer : arrList){
			list.addElement(turer);
		}	
	}

	
	public void updateBooking(ArrayList<Booking> arrList){
		bookList.clear();
		for (Booking b : arrList){
			bookList.add(b);
		}
		System.out.println("----- CHECK BOOKINGS ------");
		for(Booking book : bookList){
			System.out.println(book);
		}
		System.out.println("----- END OF BOOKINGS ------");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bookBtn){
			Tur t = tableList.getSelectedValue();
			if(t.getSeats() <= 0){
				JOptionPane.showMessageDialog(null, "Tyvärr finns det inga platser kvar på denna tur!");
			}else{
				cont.addBooking(t, persnrField.getText());
			}
		}
		if(e.getSource() == showBtn){
			cont.queryBooking(persnrField.getText());
		}
		
		if(e.getSource() == regBtn){
			Resenar person = new Resenar(persnrField.getText(), nameField.getText(), adressField.getText(), telephoneField.getText());
			personNbr = persnrField.getText();
			cont.checkResenar(person);
		}
		
		if(e.getSource() == showCityBtn){
			Tur t = tableList.getSelectedValue();
			cont.showCity(t.getCity());
		}
		
	}
	
	/**
	 * Starts the entire UI
	 * @param args
	 */
	public static void main(String[] args) {
		MainUI uiView = new MainUI();
		Controller cont = new Controller(uiView);
		
		JFrame view = new JFrame("Blomstermåla Resor");
		view.add(uiView);
		view.setResizable(false);
		view.pack();
		view.setVisible(true);
	}

}
