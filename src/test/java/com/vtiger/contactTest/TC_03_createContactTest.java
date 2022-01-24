package com.vtiger.contactTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TC_03_createContactTest 
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
		public void TC_03() throws InterruptedException, EncryptedDocumentException, IOException
		{
		 driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys(p.getProperty("uname"));
		 driver.findElement(By.xpath("//input[@name='user_password']")).sendKeys(p.getProperty("pwd"));
		 driver.findElement(By.id("submitButton")).click();	 
		 
		 Thread.sleep(5000); // additional wait till page fully loaded
		 
		 driver.findElement(By.linkText("Contacts")).click();
		 driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();
		 
		 driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys(p.getProperty("lastName"));
		 driver.findElement(By.xpath("//input[@name='firstname']")).sendKeys(p.getProperty("firstName"));
		 driver.findElement(By.xpath("//input[@name='imagename']")).sendKeys(p.getProperty("image"));
		 		 
		 driver.findElement(By.id("jscal_trigger_birthday")).click();
		 Thread.sleep(4000); 	
		 driver.findElement(By.xpath("//td[text()='12']")).click();  // to select date 12 from calender
		 Thread.sleep(4000); 	
		 		 
		 // Save the contact entry ....
		 driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])[2]")).click();
		 Thread.sleep(4000); 		 
		 
		// Validating Results WAY
		 boolean LastNameResult = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText().contains(p.getProperty("lastName"));
		 boolean FirstNameResult = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText().contains(p.getProperty("firstName"));
		 SoftAssert a=new SoftAssert();
		 a.assertEquals(LastNameResult, FirstNameResult);
		 			
	 }

		@AfterMethod
		public void closeApp() throws InterruptedException
		{
			System.out.println("************ After Method Execution **************");
			Thread.sleep(2000);
			driver.close();		
		}	
}
