package utils;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;
	int cellDataValue;
	
	public ExcelUtils(String project_path, String sheet_name) {
		XSSFWorkbook workbook;
		try {
			
			workbook = new XSSFWorkbook(project_path);
			sheet = workbook.getSheet(sheet_name);
			
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int getRowCount() {
		int rowCount = 0;
		rowCount = sheet.getPhysicalNumberOfRows();
		//System.out.println("No of rows : "+rowCount);
			
		return rowCount;
			
		}
	

	public int getColumnCount() {
		int colCount = 0;
		colCount = sheet.getRow(0).getPhysicalNumberOfCells();
		//System.out.println("No of rows : "+colCount);
		
		return colCount;
	}
	
	public String getCellDataString(int rowNum, int colNum) {
		
		String cellStrData = sheet.getRow(rowNum).getCell(colNum).getStringCellValue();
		return cellStrData;
		
	}
	
	public int getCellDataDouble(int rowNum, int colNum) {
		double cellStrData = sheet.getRow(rowNum).getCell(colNum).getNumericCellValue();
		cellDataValue = (int)cellStrData;
		return cellDataValue;
	}
	
	public void getCellDataNumber(int rowNum, int colNum) {
		
		double cellNumData = sheet.getRow(rowNum).getCell(colNum).getNumericCellValue();
		System.out.println(cellNumData);
		
	}
}
