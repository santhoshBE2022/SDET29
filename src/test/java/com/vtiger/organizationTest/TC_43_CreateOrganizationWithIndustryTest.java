package com.vtiger.organizationTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TC_43_CreateOrganizationWithIndustryTest 
{
	 WebDriver driver = null; 
	 Properties p = new Properties();
	
	@BeforeMethod
	public void openApp() throws IOException
	{
		System.out.println("************ Before Method Execution **************");
		FileInputStream fis = new FileInputStream("./src/test/resources/data.properties");
		p.load(fis);	
		
		 if(p.getProperty("browser").equals("chrome"))
				 driver = new ChromeDriver();	 
		 else 
				 driver = new FirefoxDriver();
				 
		driver.manage().window().maximize();
		driver.get(p.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	}
	
	@Test
	public void TC_43() throws InterruptedException, EncryptedDocumentException, IOException
	{
		 System.out.println("************ Test_43 Execution starts **************");
		
		 driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys(p.getProperty("uname"));
		 driver.findElement(By.xpath("//input[@name='user_password']")).sendKeys(p.getProperty("pwd"));
		 driver.findElement(By.id("submitButton")).click();	 
	
		 Thread.sleep(5000); // additional wait till page fully loaded
		 
		 driver.findElement(By.xpath("(//a[text()='Organizations'])[2]")).click();
		 driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
		 
		// Getting ORGANISATION & INDSUTRY value from excel sheet & entering to web page
		 FileInputStream fis_xl=new FileInputStream("./src/test/resources/Book1.xlsx");
		 Workbook wb = WorkbookFactory.create(fis_xl);
		 String OrgName = wb.getSheet("Sheet1").getRow(1).getCell(0).getStringCellValue();
		 String Industry = wb.getSheet("Sheet1").getRow(1).getCell(4).getStringCellValue();
		 
		 driver.findElement(By.xpath("//input[@name='accountname']")).sendKeys(OrgName);
		 WebElement industry_dd = driver.findElement(By.xpath("//select[@name='industry']"));
		 Select s = new Select(industry_dd);
		 s.selectByValue(Industry);	 	
		 
		// Save entered organization values
		 driver.findElement(By.xpath("//input[@name='button']")).click();
		 Thread.sleep(4000);
	
		 System.out.println("************ Test_43 Validation starts **************");
		 
		// Validating Results WAY-1 
		 boolean ExpResult = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText().contains(OrgName);
		 SoftAssert a=new SoftAssert();
		 a.assertTrue(ExpResult);	
				 
		// Validating Results WAY-2
		 String ExpectName = OrgName +" - Campaign Information";
		 System.out.println("Expected Name: "+ExpectName);
		 boolean Result = driver.getPageSource().contains(OrgName);		 
		 a.assertTrue(Result);			 
		 	 
		// Additional Validation WAY-3
		 String Org_Title = driver.getTitle();
	     a.assertEquals(Org_Title, p.getProperty("Org_title"));
		 		 
		// To perform LOGOUT
		 WebElement logout = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		 Actions act = new Actions(driver);
	     act.moveToElement(logout).perform();           
	     Thread.sleep(2000);
	     driver.findElement(By.linkText("Sign Out")).click();
	    
	    // To verify the Successful LOGOUT 
	     String Actual_Title = driver.getTitle();
	     a.assertEquals(Actual_Title, p.getProperty("Home_title")); 
	    
	     a.assertAll();
	     System.out.println("************ Test_43 Validation completed **************");
	     
	     System.out.println("************ Test_43 Execution completed **************");
	}
	
	@AfterMethod
	public void closeApp() throws InterruptedException
	{
		System.out.println("************ After Method Execution **************");
		Thread.sleep(2000);
		//driver.findElement(By.xpath("(//a[text()='Organizations'])[2]")).click();
		driver.close();		
	}
	
	
}
