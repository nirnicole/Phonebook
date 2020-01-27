import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


//this class generates input dialog boxes with custume parmerters inputed as array list of strings
//returns array list of inputs
public class InputDialogFrame extends JDialog {
		
	private static ArrayList<String> inputData;
	private ArrayList<JTextField> JTextFields;
	
	//constructor
	public InputDialogFrame(ArrayList<String> data,JButton cmdSubmit, JLabel messege){
		  	
			this.setTitle("Input box");
	        this.setResizable(false);
	        JPanel p = new JPanel(); 
	        p.setLayout(new GridBagLayout());		//grid box -> message, input lables and jtexts as rows, and submit button in last row.
	        GridBagConstraints gbc = new GridBagConstraints();

	        JTextFields = new ArrayList<JTextField>(0);
	        inputData = new ArrayList<String>(0);

	        int yAxis=0;

	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        p.add(messege, gbc);
	        
	        for(; yAxis<data.size(); yAxis++) {
		        gbc.gridx = 0;
		        gbc.gridy = yAxis+1;
	        	p.add(new JLabel(data.get(yAxis)), gbc);
	        	JTextFields.add(new JTextField(16));
		        gbc.gridx = 1;
		        gbc.gridy = yAxis+1;
	        	p.add(JTextFields.get(yAxis), gbc);
		        }
	        gbc.gridx = 1;
	        gbc.gridy = yAxis+1;
	        p.add(cmdSubmit, gbc);
	        this.add(p);
	        this.pack();
	        this.setLocationRelativeTo(null);
	        this.setVisible(true);
	        	        
	         
	        	        
	}//end of constructor
	
	//jtexts to array list of strings
	public void textToStrings() {
			while(!JTextFields.isEmpty())
			  inputData.add(JTextFields.remove(0).getText());	
	}
	

	//getter
	public ArrayList<String> getInputData(){
		return inputData;
	}
	

	
}//end of class
