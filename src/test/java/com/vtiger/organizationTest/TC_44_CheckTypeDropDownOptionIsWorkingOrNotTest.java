package com.vtiger.organizationTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
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

public class TC_44_CheckTypeDropDownOptionIsWorkingOrNotTest 
{
	 WebDriver driver = null; 
	 Properties p = new Properties();
	
	@BeforeMethod
	public void openApp() throws IOException
	{
		System.out.println("************ TC_44 Before Method Execution **************");
		FileInputStream fis = new FileInputStream("./src/test/resources/data.properties");
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
		driver.get(p.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
	}
	
	@Test
	public void TC_44() throws InterruptedException, EncryptedDocumentException, IOException
	{
		 System.out.println("************ Test_44 Execution starts **************");
		
		 driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys(p.getProperty("uname"));
		 driver.findElement(By.xpath("//input[@name='user_password']")).sendKeys(p.getProperty("pwd"));
		 driver.findElement(By.id("submitButton")).click();	 
	
		 Thread.sleep(4000); // additional wait 
		 
		 driver.findElement(By.xpath("(//a[text()='Organizations'])[2]")).click();
		 driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
	
		 WebElement DD = driver.findElement(By.xpath("//select[@name='accounttype']"));
		 DD.click();
		 Select s=new Select(DD);
		 List<WebElement> DD_list = s.getOptions();
		 int DD_Size = DD_list.size();
			 
		 System.out.println("************ Test_43 Validation starts **************");
		 SoftAssert a = new SoftAssert();
		 a.assertEquals(DD_Size, Integer.parseInt(p.getProperty("DD_Size")));  // parse is used as getproperty returns string value
		 a.assertTrue(DD.isEnabled());
		 a.assertTrue(DD.isDisplayed());
		 a.assertFalse(s.isMultiple());
		 		 		 				 		 
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
		driver.close();		
	}
	
	
}
