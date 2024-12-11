package electricity.billing.system;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;
public class deposit_details extends JFrame implements ActionListener {
	Choice searchMeterCho,searchMonthCho;
	JTable table;
	JButton search,print,close;
	deposit_details(){
		super("Deposit Details");
		getContentPane().setBackground(new Color(192,186,245));
		setSize(700,500);
		setLocation(400,200);
		setLayout(null);
		
		JLabel searchMeter = new JLabel("Search by Meter Number");
		searchMeter.setBounds(20,20,180,20);
		add(searchMeter);
		
		searchMeterCho = new Choice();
		searchMeterCho.setBounds(200,20,150,20);
		add(searchMeterCho);
		
		try {
			database c = new database();
			ResultSet resultSet = c.statement.executeQuery("select * from bill");
			//System.out.println("select * from Bill");
			while(resultSet.next()) {
				searchMeterCho.add(resultSet.getString("meter_no"));
				System.out.println("select * from Bill");
				}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		JLabel searchMonth = new JLabel("Search by Month");
		searchMonth.setBounds(400,20,120,20);
		add(searchMonth);		

		searchMonthCho = new Choice();
		searchMonthCho.add("January");
		searchMonthCho.add("February");
		searchMonthCho.add("March");
		searchMonthCho.add("April");
		searchMonthCho.add("May");
		searchMonthCho.add("June");
		searchMonthCho.add("July");
		searchMonthCho.add("August");
		searchMonthCho.add("September");
		searchMonthCho.add("October");
		searchMonthCho.add("November");
		searchMonthCho.add("December");	
		searchMonthCho.setBounds(520,20,150,20);
		add(searchMonthCho);
		
		
		table = new JTable();
		try {
			database c = new database();
			ResultSet resultSet = c.statement.executeQuery("select * from bill");
			table.setModel(DbUtils.resultSetToTableModel(resultSet));
			//System.out.println("a");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0,100,700,500);
		scrollPane.setBackground(Color.white);
		add(scrollPane);
		
		
		search = new JButton("Search");
		search.setBackground(Color.white);
		search.setBounds(20,70,90,20);
		search.addActionListener(this);
		add(search);
		
		print = new JButton("Print");
		print.setBackground(Color.white);
		print.setBounds(120,70,90,20);
		print.addActionListener(this);
		add(print);
		
		close = new JButton("Close");
		close.setBackground(Color.white);
		close.setBounds(600,70,90,20);
		close.addActionListener(this);
		add(close);
		
		setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==search) {
			String query_search = "select * from bill where meter_no= '"+searchMeterCho.getSelectedItem()+"' and month = '"+searchMonthCho.getSelectedItem()+"'";
		//	System.out.println("select * from bill where meter_no= '\"+searchMeterCho.getSelectedItem()+\"' and month = '\"+searchMonthCho.getSelectedItem()+\"'\";\n" + 
			//		"			");
            try {
            	database c = new database();
            	ResultSet resultSet = c.statement.executeQuery(query_search);
            	table.setModel(DbUtils.resultSetToTableModel(resultSet));
            	//System.out.println("select * from bill where meter_no= '"+searchMeterCho.getSelectedItem()+"' and month = '"+searchMonthCho.getSelectedItem()+"'");
            }catch(Exception E) {
            	E.printStackTrace();
            }
		}else if(e.getSource()==print)
		{
			try {
				table.print();
			}catch(Exception E) {
				E.printStackTrace();
			}
		}else {
			setVisible(false);
		}
	}
	public static void main(String[] args) {
		new deposit_details();
	}	
}
