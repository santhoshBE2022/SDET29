package practice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class Assignment1 
{
 public static void main(String args[]) throws IOException, InterruptedException
 {
	 WebDriver driver = null; 
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
	 
	 // getting ORGANISATION value from excel sheet & entering to webpage
	 FileInputStream fis_xl=new FileInputStream("./src/test/resources/Book1.xlsx");
	 Workbook wb = WorkbookFactory.create(fis_xl);
	 String OrgName = wb.getSheet("Sheet1").getRow(1).getCell(0).getStringCellValue();
	 String BillCity = wb.getSheet("Sheet1").getRow(1).getCell(1).getStringCellValue();
	 String Site = wb.getSheet("Sheet1").getRow(1).getCell(2).getStringCellValue();
	 int Phone = (int) wb.getSheet("Sheet1").getRow(1).getCell(3).getNumericCellValue();  // to get integer values from excel
	 String Industry = wb.getSheet("Sheet1").getRow(1).getCell(4).getStringCellValue();
	 String Bill_Address = wb.getSheet("Sheet1").getRow(1).getCell(5).getStringCellValue();
	 int Ann_Revenue = (int) wb.getSheet("Sheet1").getRow(1).getCell(6).getNumericCellValue();
	 	 
	 driver.findElement(By.xpath("//input[@name='accountname']")).sendKeys(OrgName);
	 driver.findElement(By.xpath("//input[@name='bill_city']")).sendKeys(BillCity);
	 driver.findElement(By.xpath("//input[@name='website']")).sendKeys(Site);
	 driver.findElement(By.xpath("//input[@name='phone']")).sendKeys(String.valueOf(Phone));  // send integer value through sendkeys
	 driver.findElement(By.xpath("//textarea[@name='bill_street']")).sendKeys(Bill_Address);
	 driver.findElement(By.xpath("//input[@name='notify_owner']")).click();      // to click on check box
	 driver.findElement(By.xpath("(//input[@name='cpy'])[2]")).click();
	 WebElement AnnWB = driver.findElement(By.xpath("//input[@name='annual_revenue']"));
	 AnnWB.clear();
	 AnnWB.sendKeys(String.valueOf(Ann_Revenue)); // send integer value through sendkeys
	 
	 WebElement industry_dd = driver.findElement(By.xpath("//select[@name='industry']"));
	 Select s = new Select(industry_dd);
	 s.selectByValue(Industry);	 	 
	 
	 // Save entered org values and validate 
	 driver.findElement(By.xpath("//input[@name='button']")).click();
	 Thread.sleep(4000);
	 String ActValue = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
	 	
	// To VALIDATE RESULTS 1 WAY
	 boolean Result = driver.getPageSource().contains(OrgName);
	 SoftAssert a=new SoftAssert();
	 a.assertTrue(Result);
		
	// To VALIDATE RESULTS 2 WAY
	 if(ActValue.contains(OrgName))
	 {
		 System.out.println("PASS1: Test case Pass");		
	 }
	 else
	 {
		 System.out.println("FAIL1: Test case Failed");
	 } 
	 
	 //To VALIDATE RESULTS 3 WAY
	 String Org_Title = driver.getTitle();
     System.out.println("Org title : " +Org_Title);
     if(Org_Title.equals(p.getProperty("Org_title")))
     {
    	 System.out.println("PASS2: Test passed ");		
	 }
	 else
	 {
		 System.out.println("FAIL2: Test failed");
	 }  
	
   //To VALIDATE RESULTS 4 WAY
     SoftAssert as=new SoftAssert();
	 as.assertEquals(Org_Title, p.getProperty("Org_title"));
	 
	 // To perform LOGOUT
	 WebElement ele = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
	 Actions act = new Actions(driver);
     act.moveToElement(ele).perform();   //.perform() is must        
     Thread.sleep(2000);
     driver.findElement(By.linkText("Sign Out")).click();
     
     String Actual_Title = driver.getTitle();
     System.out.println("Home title : " +Actual_Title);
     if(Actual_Title.equals(p.getProperty("Home_title")))
     {
    	 System.out.println("PASS: Logout successfull");		
	 }
	 else
	 {
		 System.out.println("FAIL: Logout failed");
	 }    
    
 }
	
}
