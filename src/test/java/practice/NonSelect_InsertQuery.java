package practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class NonSelect_InsertQuery {

  public static void main(String[] args) throws SQLException 
	{	
		Connection conn = null;
		int Result=0;
		
	 try 
	  {
		/*Step 1: load/register to mySql/database  */ 
		Driver driver = new Driver();
		DriverManager.registerDriver(driver);
		
		/*Step 2: connect to db */
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projects", "root", "root");
		
		/*Step 3: create query statement */
		Statement stat = conn.createStatement();
		String query = "insert into project values('TY_PROJ_999', 'farukh', '12/01/2022', 'BOB', 'completed', '10')";
		
		/*Step 4: execute query */
		Result = stat.executeUpdate(query);
		if(Result==1)
		{
			System.out.println("PASS: Project inserted successfully");
		}
		else
		{
			System.out.println("FAIL: Project is not inserted");
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
