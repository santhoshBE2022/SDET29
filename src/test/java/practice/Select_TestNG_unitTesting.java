package practice;

import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.mysql.jdbc.Driver;

public class Select_TestNG_unitTesting 
{
	@Test
	public void update() throws SQLException, IOException
	{ 
		Connection conn = null;
	    	    
	    Properties p = new Properties();
	    FileInputStream fis = new FileInputStream("./src/test/resources/data.properties");
	    p.load(fis);
	        
		try {
		
		Driver driver = new Driver();
		DriverManager.registerDriver(driver);
		
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projects", "root", "root");
		
		Statement stat = conn.createStatement();
		String query = "\"Select * from project";
		
		ResultSet resultSet = stat.executeQuery(query);
		boolean flag = false;
		while (resultSet.next())
		{
			String ActProName = resultSet.getString(4);
			if(ActProName.equals(p.getProperty("ExpProName")))
			{
				flag=true;
			}
		}
		
		Assert.assertTrue(flag);
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
