package electricity.billing.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;

public class generate_bill extends JFrame implements ActionListener {
	
	Choice searchmonthcho;
	
	String meter;
	JTextArea area;
	JButton bill;
	generate_bill(String meter){
		this.meter = meter;
		setSize (500,700);
		setLocation(400,30);
		setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		 
		JLabel heading = new JLabel("Generate Bill");
		
		JLabel meter_no = new JLabel("meter");
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
	//	searchmonthcho = new Choice();
	//	searchmonthcho.add("Select");
	//	searchmonthcho.add("JANUARY");
	//	searchmonthcho.add("FEBRUARY");
	//	searchmonthcho.add("MARCH");
	//	searchmonthcho.add("APRIL");
	//	searchmonthcho.add("MAY");
	//	searchmonthcho.add("JUNE");
	//	searchmonthcho.add("JULY");
	//	searchmonthcho.add("AUGUST");
//		searchmonthcho.add("SEPTEMBER");
	//	searchmonthcho.add("OCTOBER");
	//	searchmonthcho.add("NOVEMBER");
	//	searchmonthcho.add("DECEMBER");	
	    searchmonthcho.setBounds(300,200,150,20);
	    add(searchmonthcho);
		
		area = new JTextArea(50,15);
		area.setText("\n\n\t..................Click on the.........\n\t............Generate Bill");
		area.setFont(new Font("Senserif",Font.ITALIC,15));
		JScrollPane pane = new JScrollPane(area);
		bill = new JButton("Generate Bill");
		bill.addActionListener(this);
		
		add(pane);
		
		
		panel.add(heading);
		panel.add(meter_no);
		panel.add(searchmonthcho);
		add(panel,"North");
		add(bill,"South");
		
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			database c = new database();
			String smonth = searchmonthcho.getSelectedItem();
			//set area to blank first, if bill data is available then only set the data
			area.setText(" ");
			area.setText("\n Power Limited \nElectricity Bill For Month of"+smonth+",2023\n\n\n");
			ResultSet resultSet = c.statement.executeQuery("select * from new_customer where meter_no ='"+meter+"'");
			if(resultSet.next()) {
				area.append("\n   Customer Name           : "+resultSet.getString("name"));
				area.append("\n   Customer Meter Number   : "+resultSet.getString("meter_no"));
				area.append("\n   Customer Address        : "+resultSet.getString("address"));
				area.append("\n   Customer City           : "+resultSet.getString("city"));
				area.append("\n   Customer State          : "+resultSet.getString("state"));
				area.append("\n   Customer Email          : "+resultSet.getString("email"));
				area.append("\n   Customer Phone Number   : "+resultSet.getString("phone_no"));
				
			}
			System.out.println("select * from new_customer where meter_no ='"+meter+"'");
			resultSet = c.statement.executeQuery("select * from meter_info where meter_number = '"+meter+"'");
			if(resultSet.next()) {
				
				area.append("\n   Customer Meter Location          : "+resultSet.getString("meter_location"));
				area.append("\n   Customer Meter Type              : "+resultSet.getString("meter_type"));
				area.append("\n   Customer Phase Code              : "+resultSet.getString("phase_code"));
				area.append("\n   Customer Bill Type               : "+resultSet.getString("bill_type"));
				area.append("\n   Customer Days                    : "+resultSet.getString("Days"));
			}
			System.out.println("select * from meter_info where meter_number = '"+meter+"'");
			
			
		
			resultSet = c.statement.executeQuery("select * from tax");
			if(resultSet.next()) {
				area.append("\n   Cost Per Unit           : "+resultSet.getString("cost_per_unit"));
				area.append("\n   Meter Rent   : "+resultSet.getString("meter_rent"));
				area.append("\n   Service Charge        : "+resultSet.getString("service_charge"));
				area.append("\n   Service Tax          : "+resultSet.getString("service_tax"));
				area.append("\n   Swacch Bharat          : "+resultSet.getString("swacch_bharat"));
				area.append("\n   Final Tax          : "+resultSet.getString("fixed_tax"));
			}
			System.out.println("select * from tax");
			resultSet = c.statement.executeQuery("select * from bill where meter_no = '"+meter+"' and month ='"+searchmonthcho.getSelectedItem()+"'");
			String noOfUnit = "";
			String totalBill = "";
			String totalPayable = "";
			String month = "";
			if(resultSet.next()) {
				month = resultSet.getString("month");
				System.out.println("month is...:"+month);
				area.append("\n   Current Month          : "+month);
				noOfUnit = resultSet.getString("unit");
				area.append("\n   Units Consumed              : "+noOfUnit);
				totalBill = resultSet.getString("total_bill");
				area.append("\n   Total Charges              : "+totalBill);
				totalPayable = resultSet.getString("total_bill");
				area.append("\n   Total Payable               : "+totalPayable);
				
			}
			System.out.println("select * from bill where meter_no = '"+meter+"' and month ='"+searchmonthcho.getSelectedItem()+"'");
			
			//if number of units are not entered by Admin for any month , then generate bill will not be available for that month
			
			LocalDate currentdate = LocalDate.now();
			Month currentMonth = currentdate.getMonth();
			
			System.out.println("currentMonth: "+currentMonth);
			if(searchmonthcho.getSelectedItem().equalsIgnoreCase(String.valueOf(currentMonth))) {
				area.setText(" ");
				JOptionPane.showMessageDialog(null, "Unit Details Are Not Avaiable Yet For This Current Month. Please Wait Till Units Will Get Updated");
			}
		    
			else if(noOfUnit.equals("") || totalBill.equals("") || totalPayable.equals("")) {
				area.setText(" ");
				JOptionPane.showMessageDialog(null, "No Unit Details Available For The Month Of "+searchmonthcho.getSelectedItem()+". Please Connect With MSCB Customer Care.");
			}
	
		}catch(Exception E) {

			E.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new generate_bill("");
	}

}
