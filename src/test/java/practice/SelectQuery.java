package practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class SelectQuery {

	public static void main(String[] args) throws SQLException 
	{	
	Connection conn =null;
	 try 
	  {
		/*Step 1: register to mySql/database  */ 
		Driver driver = new Driver();
		DriverManager.registerDriver(driver);
		
		/*Step 2: connect to db */
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projects", "root", "root");
		
		/*Step 3: create query statement */
		Statement stat = conn.createStatement();
		String query = "Select * from project";
		
		/*Step 4: execute query */
		ResultSet resultset = stat.executeQuery(query);
		while(resultset.next())
		{
			System.out.println(resultset.getString(1) +"\t"+ resultset.getString(2) +"\t"+ resultset.getString(3) +"\t"+ resultset.getString(4) );
		}
	  }
	 catch(Exception e)
	 {
		 
	 }
	 finally
	 {
		 /*Step 5: close the connection  */
		 conn.close();
		 System.out.println("***********Connection is closed****************");
	 }
	}
	

}
