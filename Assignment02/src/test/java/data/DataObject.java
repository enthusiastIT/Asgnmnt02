package data;

import utils.ExcelUtils;

public class DataObject {

	public static Object[][] testData(String excelPath, String sheetName){
		ExcelUtils dataObj = new ExcelUtils(excelPath, sheetName);

		int rowCount = dataObj.getRowCount();
		int colCount = dataObj.getColumnCount();
		String dataValue;

		Object data[][] = new Object[rowCount-1][colCount]; 

		for(int i=1; i < rowCount; i++) 
		{
			for(int j=0; j< colCount; j++) 
			{
				dataValue = dataObj.getCellDataString(i, j);
				data[i-1][j] = dataValue;
			}
		}

		return data;
	}
	
	public static Object[][] testStatusData(String excelPath, String sheetName){
		ExcelUtils dataObj = new ExcelUtils(excelPath, sheetName);
		
		int rowCount = dataObj.getRowCount();
		int colCount = dataObj.getColumnCount();
		int dataValue;
		
		Object data[][] = new Object[rowCount-1][colCount-1]; 
				
		for(int i=1; i < rowCount; i++) 
		{
			for(int j=0; j< colCount-1; j++) 
			{
				dataValue = dataObj.getCellDataDouble(i, j);
				data[i-1][j] = dataValue;
			}
		}
		
		return data;
	}
}
