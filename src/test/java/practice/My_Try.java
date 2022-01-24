package practice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class My_Try {

	@Test(enabled = false)
	public void getIntvalue() throws EncryptedDocumentException, IOException
	{ 
				// getting ORGANISATION value from excel sheet & entering to web page
				 FileInputStream fis_xl=new FileInputStream("./src/test/resources/Book1.xlsx");
				 Workbook wb = WorkbookFactory.create(fis_xl);
				 
				 int Phone = (int)wb.getSheet("Sheet1").getRow(1).getCell(6).getNumericCellValue();  // to get integer values from excel
				 //double Phone = wb.getSheet("Sheet1").getRow(1).getCell(3).getNumericCellValue();  // to get integer values from excel
				 System.out.println("String.valueOf(Phone) : " +String.valueOf(Phone));
				 System.out.println("Phone : " +Phone);
	}
	
	@Test
	public void fileUpload() throws EncryptedDocumentException, IOException
	{ 
		WebDriver driver = new ChromeDriver();
		
	}

}
