package com.vtiger.organizationTest;

import java.io.FileInputStream;
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
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TC_68_SearchTheOrganizationbyPhoneTest 
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
		public void TC_44() throws InterruptedException, EncryptedDocumentException, IOException
		{
		 driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys(p.getProperty("uname"));
		 driver.findElement(By.xpath("//input[@name='user_password']")).sendKeys(p.getProperty("pwd"));
		 driver.findElement(By.id("submitButton")).click();	 
		 
		 Thread.sleep(5000); // additional wait till page fully loaded
		 
		 driver.findElement(By.xpath("(//a[text()='Organizations'])[2]")).click();
		 driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
		 
		// getting ORGANISATION value from excel sheet & entering to web page
		 FileInputStream fis_xl=new FileInputStream("./src/test/resources/Book1.xlsx");
		 Workbook wb = WorkbookFactory.create(fis_xl);
		 String OrgName = wb.getSheet("Sheet1").getRow(1).getCell(0).getStringCellValue();
		 String BillCity = wb.getSheet("Sheet1").getRow(1).getCell(1).getStringCellValue();
		 String Site = wb.getSheet("Sheet1").getRow(1).getCell(2).getStringCellValue();
		 int Phone = (int)wb.getSheet("Sheet1").getRow(1).getCell(6).getNumericCellValue();  // to get integer values from excel
		 String Bill_Address = wb.getSheet("Sheet1").getRow(1).getCell(5).getStringCellValue();
		 		 	 
		 driver.findElement(By.xpath("//input[@name='accountname']")).sendKeys(OrgName);
		 driver.findElement(By.xpath("//input[@name='bill_city']")).sendKeys(BillCity);
		 driver.findElement(By.xpath("//input[@name='website']")).sendKeys(Site);
		 driver.findElement(By.xpath("//input[@name='phone']")).sendKeys(String.valueOf(Phone));  // send integer value through sendkeys
		 driver.findElement(By.xpath("//textarea[@name='bill_street']")).sendKeys(Bill_Address);
		 driver.findElement(By.xpath("//input[@name='notify_owner']")).click();      // to click on check box
		 driver.findElement(By.xpath("(//input[@name='cpy'])[2]")).click();
		 
		 // Save the organization entries ....
		 driver.findElement(By.xpath("//input[@name='button']")).click();
		 Thread.sleep(4000);
		 
		 driver.findElement(By.xpath("(//a[text()='Organizations'])[2]")).click();
		 driver.findElement(By.xpath("//input[@class='txtBox']")).sendKeys(String.valueOf(Phone));
		 WebElement DD = driver.findElement(By.xpath("//select[@id='bas_searchfield']"));
		 Select s = new Select(DD);
		 s.selectByVisibleText("Phone");
		 Thread.sleep(4000);
		 
		 /* Validating the successfull search result by trying to identify presence of "No Organization Found" message
		  if no such msg found then test case passes */
		 driver.findElement(By.xpath("//input[@name='submit']")).click();
		 boolean find_org = driver.findElement(By.xpath("//span[@class='genHeaderSmall']")).isDisplayed();
		 Assert.assertFalse(find_org); 
	 }

		@AfterMethod
		public void closeApp() throws InterruptedException
		{
			System.out.println("************ After Method Execution **************");
			Thread.sleep(2000);
			driver.close();		
		}	
}
