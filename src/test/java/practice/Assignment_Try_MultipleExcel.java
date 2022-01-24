package practice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class Assignment_Try_MultipleExcel 
{
 public static void main(String args[]) throws IOException, InterruptedException
 {
	 WebDriver driver; 
	 FileInputStream fis = new FileInputStream("./src/test/resources/data.properties");
	 Properties p = new Properties();
	 p.load(fis);	 
	 
	 if(p.getProperty("browser").equals("chrome"))
	 {
		 driver = new ChromeDriver();	 
	 }
	 else
	 {
		 driver = new FirefoxDriver();
	 }
	 
	 driver.manage().window().maximize();
	 driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);	 	 
	 	  
	 driver.get(p.getProperty("url"));
	 driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys(p.getProperty("uname"));
	 driver.findElement(By.xpath("//input[@name='user_password']")).sendKeys(p.getProperty("pwd"));
	 driver.findElement(By.id("submitButton")).click();	 
	 
	 Thread.sleep(5000); // wait till page loaded
	 
	 driver.findElement(By.xpath("(//a[text()='Organizations'])[2]")).click();
	 driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
	 
	 // hardcoded ORGANISATION value entry
	/* driver.findElement(By.xpath("//input[@name='accountname']")).sendKeys("abcdefghij");
	 driver.findElement(By.xpath("//input[@name='bill_city']")).sendKeys("mysore21");
	 driver.findElement(By.xpath("//input[@name='website']")).sendKeys("www.abcde.com");
	 driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("9957959998");   */
	 
	 // getting ORGANISATION value from excel sheet & entering to webpage
	 FileInputStream fis_xl=new FileInputStream("./src/test/resources/Book1.xlsx");
	 Workbook wb = WorkbookFactory.create(fis_xl);
	 /*String OrgName = wb.getSheet("Sheet1").getRow(1).getCell(0).getStringCellValue();
	 String BillCity = wb.getSheet("Sheet1").getRow(1).getCell(1).getStringCellValue();
	 String Site = wb.getSheet("Sheet1").getRow(1).getCell(2).getStringCellValue();
	 int Phone = (int) wb.getSheet("Sheet1").getRow(1).getCell(3).getNumericCellValue();  // to get integer values from excel 
		 
	 System.out.println(OrgName);
	 System.out.println(BillCity);
	 System.out.println(Site); 
	 System.out.println(Phone);  */
	 
	 String OrgName=null, BillCity=null, Site=null;
	 int Phone=0;
	 int rowCount = wb.getSheet("Sheet1").getLastRowNum();
	 int ColCount = wb.getSheet("Sheet1").getRow(0).getLastCellNum();
	 System.out.println("Row count: " +rowCount);
	 System.out.println("Column count: " +ColCount);
	 for (int i = 1; i <= rowCount; i++) 
	 {
		 Thread.sleep(5000); // wait till page loaded
	 
		 driver.findElement(By.xpath("(//a[text()='Organizations'])[2]")).click();
		 driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();

	       for (int j = 0; j < ColCount; j++) 
	        {
	         OrgName = wb.getSheet("Sheet1").getRow(i).getCell(j).getStringCellValue();
	       	 //BillCity = wb.getSheet("Sheet1").getRow(i).getCell(++j).getStringCellValue();
	       	 //Site = wb.getSheet("Sheet1").getRow(i).getCell(++j).getStringCellValue();
	       	// Phone = (int)row.getCell(3).getNumericCellValue();	            
	        }
	     System.out.println(OrgName);
	   	 System.out.println(BillCity);
	   	 System.out.println(Site); 
	   	// System.out.println(Phone);
	   	 
	   	 driver.findElement(By.xpath("//input[@name='accountname']")).sendKeys(OrgName);
		 driver.findElement(By.xpath("//input[@name='bill_city']")).sendKeys(BillCity);
		 driver.findElement(By.xpath("//input[@name='website']")).sendKeys(Site);
		 //driver.findElement(By.xpath("//input[@name='phone']")).sendKeys(String.valueOf(Phone));  // send integer value through sendkeys
		 
		 // save entered org values and validate 
		 driver.findElement(By.xpath("//input[@name='button']")).click();
		 Thread.sleep(4000);
		 String ActValue = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		 System.out.println(ActValue); 
		 
		 //String OrgName="abcdefghij";
		 boolean Result = driver.getPageSource().contains(OrgName);
		 SoftAssert a=new SoftAssert();
		 a.assertTrue(Result);
		 
		// To VALIDATE RESULTS
		 if(ActValue.contains(OrgName))
		 {
			 System.out.println("PASS: Test case Pass");
			
		 }
		 else
		 {
			 System.out.println("FAIL: Test case Failed");
		 } 
	
	   	 
	 }
	 
	/* driver.findElement(By.xpath("//input[@name='accountname']")).sendKeys(OrgName);
	 driver.findElement(By.xpath("//input[@name='bill_city']")).sendKeys(BillCity);
	 driver.findElement(By.xpath("//input[@name='website']")).sendKeys(Site);
	 driver.findElement(By.xpath("//input[@name='phone']")).sendKeys(String.valueOf(Phone));  // send integer value through sendkeys
	 
	 // save entered org values and validate 
	 driver.findElement(By.xpath("//input[@name='button']")).click();
	 Thread.sleep(4000);
	 String ActValue = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
	 System.out.println(ActValue); 
	 
	 //String OrgName="abcdefghij";
	 boolean Result = driver.getPageSource().contains(OrgName);
	 SoftAssert a=new SoftAssert();
	 a.assertTrue(Result);
	 
	// To VALIDATE RESULTS
	 if(ActValue.contains(OrgName))
	 {
		 System.out.println("PASS: Test case Pass");
		
	 }
	 else
	 {
		 System.out.println("FAIL: Test case Failed");
	 } */
	 
	 // To perform LOGOUT
	 WebElement ele = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
	 Actions act = new Actions(driver);
     act.moveToElement(ele).perform();   //.perform() is must        
     Thread.sleep(2000);
     driver.findElement(By.linkText("Sign Out")).click();	
 }
	
	
	
}
