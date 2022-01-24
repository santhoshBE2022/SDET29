package practice;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderTestNG 
 {
		@Test(dataProvider="bookTicketDataProvider")
		public void BookTicketTest(String src, String dest, int tickets)
		{
			System.out.println("Book " +tickets +" tickets " +"From Source: " +src +"to " +" Destination: " +dest);
		}
		
		@DataProvider
		public Object[][] bookTicketDataProvider()    
		{
			Object[][] Objar = new Object[5][3];  //can have Object[5][2], Object[5][4], Object[5][5] etc but always two dimensional OBJECT array only permitted 
			Objar[0][0] = "Bangalore";
			Objar[0][1] = "Mysore";
			Objar[0][2] = 10;
			
			Objar[1][0] = "hyderabad";
			Objar[1][1] = "gulbarga";
			Objar[1][2] = 20;
			
			Objar[2][0] = "Mangalore";
			Objar[2][1] = "Mumbai";
			Objar[2][2] = 30;
			
			Objar[3][0] = "Manali";
			Objar[3][1] = "Shimla";
			Objar[3][2] = 25;
			
			Objar[4][0] = "Delhi";
			Objar[4][1] = "Kanyakumari";
			Objar[4][2] = 50;
			
			return Objar;
		}
 }

	

