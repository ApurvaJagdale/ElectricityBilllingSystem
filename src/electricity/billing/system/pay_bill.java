package electricity.billing.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
public class pay_bill extends JFrame implements ActionListener{
	Choice searchmonthcho;
	JButton pay,back;
	String meter;
	pay_bill(String meter){
		this.meter = meter;
		setSize(900,600);
		setLocation(300,150);
		setLayout(null);
		
		System.out.println("In pay bill....");
		
		
		JLabel heading = new JLabel("Pay Bill");
		heading.setFont(new Font("Tahoma",Font.BOLD,24));
		heading.setBounds(120,5,400,30);
        add(heading);		
        
        JLabel meterNumber = new JLabel("Meter Number");
        meterNumber.setBounds(35,80,200,20);
        add(meterNumber);
        
        JLabel meterNumberText = new JLabel("");
        meterNumberText.setBounds(300,80,200,20);
        add(meterNumberText);
        
        JLabel name = new JLabel("Name");
        name.setBounds(35,140,200,20);
        add(name);
        
        JLabel nameText = new JLabel("");
        nameText.setBounds(300,140,200,20);
        add(nameText);
        
        JLabel month = new JLabel("Month");
        month.setBounds(35,200,200,20);
        add(month);
       

		searchmonthcho = new Choice();
		searchmonthcho.add("Select");
		searchmonthcho.add("January");
		searchmonthcho.add("February");
		searchmonthcho.add("March");
		searchmonthcho.add("April");
		searchmonthcho.add("May");
		searchmonthcho.add("June");
		searchmonthcho.add("July");
		searchmonthcho.add("August");
		searchmonthcho.add("September");
		searchmonthcho.add("October");
		searchmonthcho.add("November");
		searchmonthcho.add("December");	
		searchmonthcho.setBounds(300,200,150,20);
		add(searchmonthcho);
		
		JLabel unit = new JLabel("Unit");
        unit.setBounds(35,260,200,20);
        add(unit);
        
        JLabel unitText = new JLabel("");
        unitText.setBounds(300,260,200,20);
        add(unitText);
		
        JLabel totalBill = new JLabel("Total Bill");
        totalBill.setBounds(35,320,200,20);
        add(totalBill);
        
        JLabel totalBillText = new JLabel("");
        totalBillText.setBounds(300,320,200,20);
        add(totalBillText);
        
       
		

		JLabel status = new JLabel("Status");
        status.setBounds(35,380,200,20);
        add(status);
        
        JLabel statusText = new JLabel("");
        statusText.setBounds(300,380,200,20);
        statusText.setForeground(Color.RED);
        add(statusText);
        
        try {
        	database c = new database();
        	ResultSet resultSet = c.statement.executeQuery("select * from new_customer where meter_no = '"+meter+"'");
        	while(resultSet.next()) {
        		meterNumberText.setText(meter);
        		nameText.setText(resultSet.getString("name"));
        		
        	}
        	
        }catch(Exception e) {
        	e.printStackTrace();
        }
        
        searchmonthcho.addItemListener(new ItemListener() {
        	@Override
        	public void itemStateChanged(ItemEvent e) {
        		System.out.println("addItemlistener.....");
        		//set all text field value to blank
        		unitText.setText(" ");
        		totalBillText.setText(" ");
        		statusText.setText(" ");
        		
        		database c = new database();
        		System.out.println("select * from bill where meter_no ='"+meter+"' and month ='"+searchmonthcho.getSelectedItem()+"'");
        		try {
        			ResultSet resultSet = c.statement.executeQuery("select * from bill where meter_no ='"+meter+"' and month ='"+searchmonthcho.getSelectedItem()+"'");
        			while(resultSet.next()) {
        				unitText.setText(resultSet.getString("unit"));
        				totalBillText.setText(resultSet.getString("total_bill"));
        				String billstatus = resultSet.getString("status");
        				statusText.setText(billstatus);
        				
        				System.out.println("unit: "+resultSet.getString("unit"));
        				System.out.println("total bill: "+resultSet.getString("total_bill"));
        				System.out.println("status: "+resultSet.getString("status"));
        				
        				System.out.println("billstatus: "+billstatus);
        				
        				//If bill is already paid don't show 'Pay' button
        				
						if (billstatus.equals("Paid")) {
							System.out.println("Disable pay");

							pay.setVisible(false);
						}
        			}
        		}catch(Exception E) {
        			E.printStackTrace();
        		}
        		
        	}
        });
       

        
        pay = new JButton("Pay");
        pay.setBackground(Color.black);
        pay.setForeground(Color.white);
        pay.setBounds(100,460,100,25);
        pay.addActionListener(this);
        add(pay);
        
        
        
        back = new JButton("Back");
        back.setBackground(Color.black);
        back.setForeground(Color.white);
        back.setBounds(230,460,100,25);
        back.addActionListener(this);
        add(back);
        
        setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==pay) {
			try {
				database c = new database();
				c.statement.executeUpdate("update bill set status = 'Paid' where meter_no ='"+meter+"' and month ='"+searchmonthcho.getSelectedItem()+"'");
			}catch(Exception E) {
				E.printStackTrace();
			}
			setVisible(false);
			new payment_bill(meter);
		}else {
			setVisible(false);
			
		}
	}
	public static void main(String args[]) {
		new pay_bill("");
		
	}

}
