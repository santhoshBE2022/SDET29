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

public class TC_19_sendingmailTest 
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
			driver.manage().timeouts().implicitlyWait(50,TimeUnit.SECONDS);
		}
		
		@Test
		public void TC_01() throws InterruptedException, EncryptedDocumentException, IOException
		{
		 driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys(p.getProperty("uname"));
		 driver.findElement(By.xpath("//input[@name='user_password']")).sendKeys(p.getProperty("pwd"));
		 driver.findElement(By.id("submitButton")).click();	 
		 
		 Thread.sleep(2000); // additional wait till page fully loaded
		 
		 driver.findElement(By.linkText("Contacts")).click();
		 Thread.sleep(2000);
		 driver.findElement(By.xpath("(//input[@value='Send Mail'])[1]")).click();
				 
		 Alert alt = driver.switchTo().alert();
		 String alertMsg = alt.getText();
		 Thread.sleep(4000);
		 alt.accept();
		 
		// Validating Results by comparing alert messages
		 Assert.assertEquals(alertMsg, p.getProperty("mailMsg"));				 		
		
		 
	 }

		@AfterMethod
		public void closeApp() throws InterruptedException
		{
			System.out.println("************ After Method Execution **************");
			Thread.sleep(2000);
			//driver.close();		
		}	
}
