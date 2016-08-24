package blomstermala;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

public class BookingUI extends JPanel {

	private DefaultListModel<Booking> list = new DefaultListModel<Booking>();
	private JList<Booking> tableList = new JList<Booking>(list);
	
	public BookingUI() {
		setLayout(null);
		setPreferredSize(new Dimension(450,300));
		
		
		tableList.setBounds(0, 0, 450, 300);
		add(tableList);
	}
	
	
	public void updateList(ArrayList<Booking> arrList){
		list.clear();
		for (Booking b : arrList){
			list.addElement(b);
		}	
	}

}
