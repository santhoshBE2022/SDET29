package practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.Test;

import com.mysql.jdbc.Driver;

public class NonSelect__UpdateQuery_TestNG {
	@Test
	public void update() throws SQLException
	{ Connection conn = null;
		try {
		
		Driver driver = new Driver();
		DriverManager.registerDriver(driver);
		
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projects", "root", "root");
		
		Statement stat = conn.createStatement();
		String query = "update project set project_name='WINDOW' WHERE project_id='TY_PROJ_969'";
		
		int result = stat.executeUpdate(query);
		
		if(result == 1)
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
		conn.close();
		System.out.println("Connection closed");
	}
	}

}
