package practice;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class WriteToExcel 
{
	public static void main(String[] args) throws Throwable{
		
		FileInputStream fls= new FileInputStream("./src/test/resources/Book1.xlsx");
		Workbook wb = WorkbookFactory.create(fls);
		Sheet sh = wb.getSheet("Sheet1");
		Row row = sh.getRow(3);
		Cell col = row.createCell(3); //OR below line .... for this at-least one value in that row entry should be present
		//Cell col = row.getCell(3);  // for this that cell can't be empty, some entry in that cell 3 should be there
		col.setCellValue("PASS");
		
		FileOutputStream fos= new FileOutputStream("./src/test/resources/Book1.xlsx");
		wb.write(fos);
		wb.close();
		
	}

}
