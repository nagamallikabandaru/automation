package com.helpchat.tests.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
//import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;


public class ExcelUtil {
	
	public static HashMap<String, HashMap<String, String>> readXlsFile(
			String dataFilePath) {
		HashMap<String, HashMap<String, String>> dataMap = new HashMap<String,HashMap<String, String>>();
		final Logger logger=Logger.getLogger(ExcelUtil.class.getName());
//		Logger logger=new logger(ExcelUtil.class);
//		Logger logger =  LoggerFactory.getLogger(ExcelUtil.getClass());
		try{
			
			FileInputStream file = new FileInputStream(new File(dataFilePath));
			HSSFWorkbook workBook = new HSSFWorkbook(file);
			HSSFSheet sheet = workBook.getSheetAt(0);					
			int noOfCols=sheet.getRow(0).getPhysicalNumberOfCells();
			ArrayList<String> colHeaders = new ArrayList<String>();
			
			
			Row row = null;
			//Considering first row as header
//			logger.info("Getting the header");
			for (int j = 0; j < noOfCols; j++) {
				colHeaders.add(sheet.getRow(0).getCell(j).getStringCellValue());
			}
			for(int rowCnt=1;rowCnt<=sheet.getLastRowNum();rowCnt++){	
				String rowKey=null;
				HashMap<String, String> rowMap = new HashMap<String, String>();	
				ArrayList<String> colValues = new ArrayList<String>();
				row=sheet.getRow(rowCnt);
				for(int colCnt=0;colCnt<row.getPhysicalNumberOfCells();colCnt++){
					Cell cell=row.getCell(colCnt);
//					logger.info("Cell value"+cell.getCellType());
					if(colCnt==0){
	                    colValues.add(cell.getStringCellValue());
	                    rowKey=cell.getStringCellValue();
					}else{
						colValues.add(cell.getStringCellValue());						
					}
				
				}
				for (int j = 0; j < noOfCols; j++) {
					rowMap.put(colHeaders.get(j), colValues.get(j));
				}

				dataMap.put(rowKey, rowMap);
			}						   		    
		    workBook.close();
		    file.close();
		}catch(Exception e){
			e.printStackTrace();
		}

		return dataMap;
	}
}