//generates contact instances
public class Contact {
	
	private String firstName;
	private String lastName;
	private String phoneNumber;

	//constructor
        public Contact(String first, String last, String phoneNumber) {
            this.firstName = first;
            this.lastName = last;
            this.phoneNumber = phoneNumber;
        }
       
        //getters
        //
        public String getFirstName() {return firstName;}
        public String getLastName() {return lastName;}
        public String getPhoneNumber() {return phoneNumber;}
        public Object[] getContactObjectArray() {return new Object[] {firstName,lastName,phoneNumber};}

    	//stringing the instance by formal presentation rules separated by tabs.
    	public String toString()
    	{
    		String str= firstName + "\t" + lastName + "\t" + phoneNumber;		// "name	lastname	phonenumber"
    		return str;
    	}

}//end of class
