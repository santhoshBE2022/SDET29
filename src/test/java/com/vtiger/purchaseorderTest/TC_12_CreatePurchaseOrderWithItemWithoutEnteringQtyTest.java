package com.vtiger.purchaseorderTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.ClickAction;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TC_12_CreatePurchaseOrderWithItemWithoutEnteringQtyTest 
{
	Properties pro;
	WebDriver driver = null;
	
	@BeforeMethod
	public void openApp() throws IOException
	{
		FileInputStream file = new FileInputStream("./src/test/resources/data.properties");
		pro = new Properties();
		pro.load(file);
		
		String BROWSER = pro.getProperty("browser");			
		if(BROWSER.equals("chrome"))
			driver = new ChromeDriver();
		else if(BROWSER.equals("firefox"))
			driver = new FirefoxDriver();
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

	}
	@Test
	public void Test() throws IOException, InterruptedException
	{
		String URL = pro.getProperty("url");	
		String USERNAME = pro.getProperty("uname");
		String PASSWORD = pro.getProperty("pwd");
		
		driver.get(URL);
		driver.findElement(By.name("user_name")).sendKeys(USERNAME);
		driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
		driver.findElement(By.id("submitButton")).click();
		
		Actions a = new Actions(driver);
		a.moveToElement(driver.findElement(By.xpath("//a[text()=\"More\"]"))).perform();
		Thread.sleep(2000);
		driver.findElement(By.name("Purchase Order")).click();
		driver.findElement(By.xpath("//img[@alt=\"Create Purchase Order...\"]")).click();
		
		String subject = pro.getProperty("subject");
		String status = pro.getProperty("status");
		driver.findElement(By.name("subject")).sendKeys(subject);
		
		WebElement ele = driver.findElement(By.name("postatus"));
		Select s = new Select(ele);
		s.selectByValue(status);
		
		driver.findElement(By.xpath("//img[@title=\"Select\"]")).click();
		Thread.sleep(3000);
		
		Set<String> windows01 = driver.getWindowHandles();
		Iterator<String> iterator01 = windows01.iterator();
		String parentWindow01 = iterator01.next();
		String childwindow01 = iterator01.next();
		driver.switchTo().window(childwindow01);
		driver.findElement(By.xpath("//a[text()=\"Mary\"]")).click();
		driver.switchTo().window(parentWindow01);
		
		driver.findElement(By.xpath("//img[@id=\"searchIcon1\"]")).click();
		
		Thread.sleep(3000);
		
		Set<String> windows02 = driver.getWindowHandles();
		Iterator<String> iterator02 = windows02.iterator();
		String parentWindow02 = iterator02.next();
		String childwindow02 = iterator02.next();
		driver.switchTo().window(childwindow02);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@value=\"57\"]")).click();
		driver.findElement(By.xpath("//input[@value=\"Select Products\"]")).click();
		driver.switchTo().window(parentWindow02);
		
		driver.findElement(By.xpath("//input[@title=\"Save [Alt+S]\"]")).click();
		
		Thread.sleep(5000);
		Alert al = driver.switchTo().alert();
		String ab = al.getText();
		System.out.println(ab);
		
		System.out.println(pro.getProperty("alert_msg"));
		Assert.assertEquals(ab,pro.getProperty("alert_msg") );
		al.accept();
	}
	
	@AfterMethod
	public void closeApp() throws InterruptedException
	{
		Thread.sleep(3000);
		driver.close();
	}

}
