import java.util.Map;
import java.util.TreeMap;
import javax.swing.JOptionPane;

//phone book map
//generates a treemap object that denies mor than 1 instance of a phone number in the data structur.
//also sort the contacts inputed by phone number.
public class BookMap {

	private static Map<String, Contact> myMapPhone;
	
	//constructor			
	public BookMap() {
		myMapPhone = new TreeMap<>();
	}
	
	//adding contact to each map
	public void add(Contact newCon) {

			if(! myMapPhone.containsKey(newCon.getPhoneNumber()))
				myMapPhone.put(newCon.getPhoneNumber(), newCon);
			else JOptionPane.showMessageDialog(null,
        			"Contact number already in use by: " + myMapPhone.get(newCon.getPhoneNumber()), "Error",
        			JOptionPane.INFORMATION_MESSAGE);
	}
	
	//removing contact
	public Contact remove(Contact con1) {
		Contact removed;
		try {
			removed = myMapPhone.remove(con1.getPhoneNumber());
		}catch(NullPointerException err) {
			removed = null;
		}
		
		return removed;
	}
	
	//getters
	//
	public int Size() {
		return myMapPhone.size();
	}

	public Map<String, Contact> getMapByPhone() {
		return myMapPhone;
	}

}//end of class
