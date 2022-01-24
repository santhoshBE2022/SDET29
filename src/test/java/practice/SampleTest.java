package practice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SampleTest 
{
 public static void main(String args[]) throws IOException
 {
	 WebDriver driver; 
	 FileInputStream fis = new FileInputStream("./src/test/resources/data.properties");
	 Properties p = new Properties();
	 p.load(fis);
	 
	 // To read data from console .... not popularly used.
	/* Scanner s = new Scanner(System.in);
	 System.out.println("Enter Browser name");
	 String Browser = s.next();  */
	 
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
	 
	 
	 
 }
	
	
	
}
