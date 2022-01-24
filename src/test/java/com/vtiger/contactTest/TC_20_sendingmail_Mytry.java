package com.vtiger.contactTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
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

public class TC_20_sendingmail_Mytry 
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
		 driver.findElement(By.xpath("(//input[@id='22'])[1]")).click();
		 Thread.sleep(3000);
		 driver.findElement(By.xpath("(//input[@value='Send Mail'])[1]")).click();
		 Thread.sleep(2000);
		 driver.findElement(By.xpath("(//input[@name='semail'])[2]")).click();
		 Thread.sleep(2000);
		 driver.findElement(By.xpath("//input[@name='Select']")).click();
		 
		 Thread.sleep(4000);
		 Set<String> window = driver.getWindowHandles();
		 Iterator<String> itr = window.iterator();
		 String ParentWindow = itr.next();
		 String ChildWindow = itr.next();
		 driver.switchTo().window(ChildWindow);
		 driver.findElement(By.id("cc_name")).sendKeys("piyushsrivastava@gmail.com");
		 driver.findElement(By.id("subject")).sendKeys("My resume");
		 //driver.findElement(By.xpath("//textarea[@class='cke_source cke_enable_context_menu']")).sendKeys("hi ... how are you");
		 Thread.sleep(4000);
		 driver.findElement(By.xpath("(//input[@name='Send'])[1]")).click();		
	 }

		@AfterMethod
		public void closeApp() throws InterruptedException
		{
			System.out.println("************ After Method Execution **************");
			Thread.sleep(2000);
			//driver.close();		
		}	
}
