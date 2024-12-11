package electricity.billing.system;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class database {
	
	Connection connection;
	//java.sql.//
	Statement statement;
	database()
	{
		try {
		connection = DriverManager.getConnection("jdbc:postgresql:bill_system","postgres","postgres");
		statement = connection.createStatement();
	}catch(Exception e) {
		e.printStackTrace();
	}
	}
}
