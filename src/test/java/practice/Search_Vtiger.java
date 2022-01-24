package practice;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

public class Search_Vtiger {

	public static void main(String[] args) throws IOException, InterruptedException 
	{
		 WebDriver driver = null; 
		 FileInputStream fis = new FileInputStream("./src/test/resources/data.properties");
		 Properties p = new Properties();
		 p.load(fis);	 
		 
		 if(p.getProperty("browser").equals("chrome"))
		 	 driver = new ChromeDriver();	 
		 else
		 	 driver = new FirefoxDriver();
		 		 
		 driver.manage().window().maximize();
		 driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);	 	 
		 	  
		 driver.get(p.getProperty("url"));
		 driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys(p.getProperty("uname"));
		 driver.findElement(By.xpath("//input[@name='user_password']")).sendKeys(p.getProperty("pwd"));
		 driver.findElement(By.id("submitButton")).click();	 
		 
		 Thread.sleep(5000); // additional wait till page fully loaded
		 
		 driver.findElement(By.xpath("(//a[text()='Organizations'])[2]")).click();
		 driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
		 
		 driver.findElement(By.xpath("//input[@name='accountname']")).sendKeys("wwww7");
		 driver.findElement(By.xpath("//input[@name='bill_city']")).sendKeys("Blore");
		 driver.findElement(By.xpath("//input[@name='button']")).click();
		 Thread.sleep(4000);
		 
		 driver.findElement(By.xpath("(//a[text()='Organizations'])[2]")).click();
		 driver.findElement(By.xpath("//input[@class='txtBox']")).sendKeys("wwww");
		 Thread.sleep(4000);
		 
		 //driver.findElement(By.xpath("//span[@class='genHeaderSmall']")).click();
		 driver.findElement(By.xpath("//input[@name='submit']")).click();
		 WebElement Msg_ele = driver.findElement(By.xpath("//span[@class='genHeaderSmall']"));
		 System.out.println(Msg_ele.isDisplayed());
		 String Actual_errorMsg = Msg_ele.getText();
		 
		 System.out.println("Actual_errorMsg : " +Actual_errorMsg);
		 System.out.println("Exp_errorMsg : " +p.getProperty("Exp_errorMsg"));
		 Assert.assertEquals(p.getProperty("Exp_errorMsg"), Actual_errorMsg);
		 
	}

}
