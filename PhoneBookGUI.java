import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

//phone book graphic object, implements basic PB functions such as addin/deleting/searching contact and importing/exporting contact lists
public class PhoneBookGUI extends JFrame {
	
	private JPanel container = new JPanel();
    private JTable tbl; 
    private DefaultTableModel dm;
	private BookMap phoneBookMap = new BookMap();
	private TableRowSorter<TableModel> rowSorter;
	private List<RowSorter.SortKey> sortKeys;
	private InputDialogFrame db;					// i chose to implement my own  input dialog box rather then use a costume confirm box, hope you would like
	private JButton cmdSubmitAdd = new JButton("submit");	//for the input box
	
	
	//constructor
	public PhoneBookGUI(){ 
		super("Phone Book");
        this.setSize(350, 380);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
		container.setLayout(new BorderLayout());
        JScrollPane scroll = new JScrollPane(container,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);        
        scroll.setViewportView(container);
        // add header of the table
		String[] header = {"First Name",
	            "Last Name",
	            "Phone number"};
        
		//adding menu bar and menu items
        JMenuBar mb=new JMenuBar();  
        JMenu menuAction=new JMenu("Action");  
        JMenu menuSource=new JMenu("File");  
        JMenuItem menuImport, menuExport, menuExit;  
        JMenuItem menuAdd, menuDelete;  
        menuImport=new JMenuItem("Import...");  
        menuExport=new JMenuItem("Export...");  
        menuExit=new JMenuItem("Exit");  
        menuAdd=new JMenuItem("Add contact");  
        menuDelete=new JMenuItem("Delete contact");  
        menuSource.add(menuImport);  menuSource.add(menuExport);  menuSource.add(menuExit);
        menuAction.add(menuAdd); menuAction.add(menuDelete); 
        mb.add(menuSource);  
        mb.add(menuAction);  
        this.setJMenuBar(mb);        
 
        
        final JFileChooser fileChooser = new JFileChooser();
        // Open the dialog using null as parent component if you are outside a
        // Java Swing application otherwise provide the parent comment instead
        //importing file
        menuImport.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ev) {

	        	phoneBookMap.getMapByPhone().clear();
	        	
	        	int returnVal = fileChooser.showOpenDialog(null);
	        	if (returnVal == JFileChooser.APPROVE_OPTION) {
	        		// Retrieve the selected file
	        		File file = fileChooser.getSelectedFile();
	        		try (FileInputStream fis = new FileInputStream(file)) {
	        			// Do something here
	        			
	        			List<String> lines = Files.readAllLines(Paths.get(fileChooser.getSelectedFile().getPath()),Charset.defaultCharset() );
		        		String[] parsedStrings = new String[3];
	        			for(String c : lines)
	        			{
	        				parsedStrings = c.split("\t");
			  				Contact e1 = new Contact( parsedStrings[0] , parsedStrings[1] , parsedStrings[2] );
							phoneBookMap.add(e1);		
	        			}
	        			updateTable();
		        			
	        		} catch (FileNotFoundException e) {
	        			// TODO Auto-generated catch block
	                	JOptionPane.showMessageDialog(null,
	                			"file not found", "Error",
	                			JOptionPane.INFORMATION_MESSAGE);
	        			e.printStackTrace();
	        		} catch (IOException e) {
	        			// TODO Auto-generated catch block
	                	JOptionPane.showMessageDialog(null,
	                			"syntax error, data corrupted.", "Error",
	                			JOptionPane.INFORMATION_MESSAGE);
	        			e.printStackTrace();
	        		} catch (ArrayIndexOutOfBoundsException e) {
        			// TODO Auto-generated catch block
                	JOptionPane.showMessageDialog(null,
                			"syntax error, data corrupted.", "Error",
                			JOptionPane.INFORMATION_MESSAGE);
        			e.printStackTrace();
        		}
	        	}
			  }
	      });
        
        //exporting file
        menuExport.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ev) {	         	
            try {
            	boolean approveFlag = false;
	        	JFileChooser folderChooser = new JFileChooser();
	        	folderChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	        	
        		while(!approveFlag)
        		{
		        	int user = folderChooser.showSaveDialog(null);
		        	if (user == JFileChooser.APPROVE_OPTION) 
		        	{
	
			     		String fileName = folderChooser.getSelectedFile().getName();
			     		if(fileName.length()>3 && fileName.substring(fileName.length()-4).equals(".txt") )
			     				fileName = fileName.substring(0, fileName.length()-4);
			     		
			       		File folder = folderChooser.getCurrentDirectory();	
		        		File f = new File(folder,fileName + ".txt");
			        	
			        	//check if the file exists  
			       		if(!f.createNewFile())
			       		{	
			       			int dialogButton = JOptionPane.YES_NO_OPTION;
			       			int dialogResult = JOptionPane.showConfirmDialog (null, fileName + ".txt" + " already exist.\n"
			       					+ "Do you want to repace it?","Warning",dialogButton);
			       			if(dialogResult == JOptionPane.NO_OPTION)
			       				continue;
				          }
		       			
	       				FileOutputStream fileOut = new FileOutputStream (f,false);
	       				OutputStreamWriter write =new OutputStreamWriter(fileOut);
		                for (Contact c : phoneBookMap.getMapByPhone().values())
		                {
			               	String s= c.toString() + "\n";
							write.write(s);
		               	}
		                approveFlag = true;
		                write.close();
		                fileOut.close();		       		
		       		
		        	} else
		        		approveFlag = true;    	
		          }
            } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  }
	      });
        
        //
        
        //terminate program
        menuExit.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ev) {
	        	System.exit(0);
				  }
	      });
        
        //mark and delete contact
		menuDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selRow =tbl.getSelectedRow();
                if(selRow != -1) {
                    selRow = tbl.convertRowIndexToModel(tbl.getSelectedRow());
                	phoneBookMap.remove(
                			new Contact(
                					(String)dm.getValueAt(selRow, 0),
                					(String)dm.getValueAt(selRow, 1),
                					(String)dm.getValueAt(selRow, 2)
                					));	
                	updateTable();	
                }else {
                	JOptionPane.showMessageDialog(null,
                			"Please mark a row  first!", "Error",
                			JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
		
		//adding new contact
		menuAdd.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ev) {

				  ArrayList<String> data = new ArrayList<String>(0);
				  data.add("First Name:");
				  data.add("Last Name:");
				  data.add("Phone Number:");
				  db = new InputDialogFrame(data,cmdSubmitAdd, new JLabel("Please input contact details."));
				  }
	      });

		ButtonListenerCMD listener = new ButtonListenerCMD();
		cmdSubmitAdd.addActionListener(listener);

    	// create table and table model
    	tbl = new JTable();
    	dm = new DefaultTableModel(0, 0);
        dm.setColumnIdentifiers(header);
        tbl.setModel(dm);
        
        //create row sorter
        rowSorter = new TableRowSorter<TableModel>(tbl.getModel());
        tbl.setRowSorter(rowSorter);
        sortKeys = new ArrayList<RowSorter.SortKey>();
        sortKeys.add( new RowSorter.SortKey(0, SortOrder.DESCENDING));		//sorting by first name as default
        rowSorter.setSortKeys(sortKeys);
        rowSorter.sort();
        
        
        //adjusting filter panel
        JTextField jtblFilter = new JTextField();
        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.add(new JLabel("Specify a word to match:"), BorderLayout.WEST);
        filterPanel.add(jtblFilter, BorderLayout.CENTER);
        
        //implement the filter and overide original methods to adjust jtable
        jtblFilter.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtblFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter( text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtblFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter(text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });//end of filter
        
        //add objects to container
        container.add(tbl.getTableHeader(),   BorderLayout.PAGE_START);
        container.add(tbl, BorderLayout.CENTER);
        
        //scroll contains the container, adding it all to frame.
        this.add(scroll, BorderLayout.CENTER);
        this.add(filterPanel,BorderLayout.PAGE_END);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    
        
        
	       
	}
	
	//clearing and input the data structure back into the table after every change made
	private void updateTable() {
		dm.setRowCount(0);
		for(Map.Entry<String, Contact> entry : phoneBookMap.getMapByPhone().entrySet())
			dm.addRow(new Object[] {entry.getValue().getFirstName(),entry.getValue().getLastName(),entry.getValue().getPhoneNumber() });
	}
	
	//action listner for the submit button sent to the input dialog box
	public class ButtonListenerCMD implements ActionListener { 
		  public void  actionPerformed(ActionEvent e) {
			  			
			
			  //send add action
			  if(e.getSource() == cmdSubmitAdd) {
				
				  db.dispose();
				  db.textToStrings();
				  Contact e1 = new Contact( db.getInputData().remove(0), db.getInputData().remove(0), db.getInputData().remove(0));
				  phoneBookMap.add(e1);				     				  
				  
				  updateTable();
				  }

			  repaint();
			  }

	}
	

}//end of class
