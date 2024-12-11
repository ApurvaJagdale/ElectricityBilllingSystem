package electricity.billing.system;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
public class Signup extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Choice loginASCho;
	TextField meterText,EmployerText,userNameText,nameText,passwordText;
	JButton create,back;
	Signup(){
		super("Signup Page");
		getContentPane().setBackground(new Color(168,203,255));
		
		JLabel createAs = new JLabel("Create Account");
		createAs.setBounds(30,50,125,20);
		add(createAs);
		
		loginASCho = new Choice();
		loginASCho.add("Admin");
		loginASCho.add("Customer");
		loginASCho.setBounds(170,50,120,20);
		add(loginASCho);
		
		JLabel meterNo = new JLabel("Meter Number");
		meterNo.setBounds(30,100,125,20);
		meterNo.setVisible(false);
		add(meterNo);
		
		meterText = new TextField();
		meterText.setBounds(170,100,125,20);
		meterText.setVisible(false);
		add(meterText);
		
		JLabel Employer = new JLabel("Employer ID");
		Employer.setBounds(30,100,125,20);
		Employer.setVisible(true);
		add(Employer);
		
		EmployerText = new TextField();
		EmployerText.setBounds(170,100,125,20);
		EmployerText.setVisible(true);
		add(EmployerText);
		
		JLabel userName = new JLabel("UserName");
		userName.setBounds(30,140,125,20);
		add(userName);
		
		userNameText = new TextField();
		userNameText.setBounds(170,140,125,20);
		//userNameText.setVisible(true);
		add(userNameText);
		
		
		JLabel name = new JLabel("Name");
		name.setBounds(30,180,125,20);
		add(name);
		
		nameText = new TextField("");
		nameText.setBounds(170,180,125,20);
		add(nameText);
		
		meterText.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				try {
					database c = new database();
					String query = "select * from Signup where meter_no = '"+meterText.getText()+"'";
					ResultSet resultSet = c.statement.executeQuery("select * from Signup where meter_no = '"+meterText.getText()+"'");
					System.out.println("query in Signup....."+query);
				if(resultSet.next()) {
					
					System.out.println("name in Signup....."+resultSet.getString("name"));
					nameText.setText(resultSet.getString("name"));
				}
				}catch(Exception E) {
					E.printStackTrace();
				}
			}
		});
		
		JLabel password = new JLabel("password");
		password.setBounds(30,220,120,20);
		add(password);
		
		passwordText = new TextField();
		passwordText.setBounds(170,220,125,20);
		add(passwordText);
		
		
		loginASCho.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				String user = loginASCho.getSelectedItem();
				System.out.println("Signup Role selected...:"+user);
				if(user.equals("Customer")) {
					Employer.setVisible(false);
					//System.out.println("nameText.setEditable(false);");
	         		nameText.setEditable(false);
					EmployerText.setVisible(false);
					meterNo.setVisible(true);
					meterText.setVisible(true);
					//Open signup page for custmer here
					//new newCustomer();
					
				}else {
					Employer.setVisible(true);
					EmployerText.setVisible(true);
					meterNo.setVisible(false);
					meterText.setVisible(false);
				}
			}
		});
		
		create = new JButton("Create");
		create.setBackground(new Color(66,127,219));
		create.setForeground(Color.black);
		create.setBounds(50,285,100,25);
		create.addActionListener(this);
		add(create);
		
		back = new JButton("Back");
		back.setBackground(new Color(66,127,219));
		back.setForeground(Color.black);
		back.setBounds(180,285,100,25);
		back.addActionListener(this);
		add(back);
		
		ImageIcon boyIcon = new ImageIcon(ClassLoader.getSystemResource("boy.png"));
		Image boyImg = boyIcon.getImage().getScaledInstance(250,250,Image.SCALE_DEFAULT);
		ImageIcon boyIcon2 = new ImageIcon(boyImg);
		JLabel boyLabel = new JLabel(boyIcon2);
		boyLabel.setBounds(320,30,250,250);
		add(boyLabel);		
		
		setSize(600,380);
		setLocation(500,200);
		setLayout(null);
		setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==create) {
			String sloginAs = loginASCho.getSelectedItem();
			String susername = userNameText.getText();
			String sname = nameText.getText();
			String spassword = passwordText.getText();
			String smeter = meterText.getText();
			System.out.println("In Signup role...: "+sloginAs);
			try {
				database c = new database();
				String query = null;
				if(sloginAs.equals("Admin")) 
				{
					System.out.println("In Signup Admin role...");
				query = "insert into Signup values ('"+smeter+"','"+susername+"','"+sname+"','"+spassword+"','"+sloginAs+"')";
				}else {
					System.out.println("In Signup customer role...");
					query = "update Signup set username ='"+susername+"',password = '"+spassword+"',usertype = '"+sloginAs+"'where meter_no = '"+smeter+"'"; 
					
					//query = "insert into Signup values('"+smeter+"','"+susername+"','"+sname+"','"+spassword+"','"+sloginAs+"')";
				}
				System.out.println("Final query: "+query);
				c.statement.executeUpdate(query);
				
				JOptionPane.showMessageDialog(null,"Account Created");
				setVisible(false);
				new Login();
				
			}catch(Exception E){
				JOptionPane.showMessageDialog(null,""+susername+" same Username is already available.Please provide unique username.");
			//E.printStackTrace();
			System.out.println("Final query: "+E.getMessage());
			
			}
		}else if(e.getSource()==back) {
			setVisible(false);
			new Login();
		}
	}
	public static void main(String[] args) {
	new Signup();	
	}
}
