package electricity.billing.system;

import java.awt.*;

import javax.swing.*;

public class Splash extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Splash(){
		ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("Splash.jpg"));
		Image imageOne = imageIcon.getImage().getScaledInstance(600,400,Image.SCALE_DEFAULT);
		ImageIcon imageIcon2 = new ImageIcon( imageOne);
		JLabel imageLabel = new JLabel(imageIcon2);
		add(imageLabel);
		
		
		setSize(600,400);
		setLocation(500,200);
		setVisible(true);
		
		try {
			Thread.sleep(3000);
			setVisible(false);
			
			new Login();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		new Splash();
}
}