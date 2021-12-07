package JAVA_AutomationUtilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @author SAQUEEB ALI
 * @version 1.0
 * 
 */

public class FileLib {
	
	public static Workbook wb;
	/**
	 * This method is used to retrieve property value from a property file
	 * 
	 * @param key take property key as specified in property file
	 * @param propertyFile take property file path in String format
	 * @throws IOException
	 * @return value corresponding to the key specified
	 */
	public String getPropertyValue(String key, String propertyFile) throws IOException {
		FileInputStream fis = new FileInputStream("./data/commondata.property");
		Properties p = new Properties();
		p.load(fis);
		return p.getProperty(key);
	}

	/**
	 * This method is used to set value in specified excel file
	 * 
	 * @param excelFile take excel file path
	 * @param SheetName take Sheet name in String format
	 * @param row       take row no in int format
	 * @param cell      take cell no in int format
	 * @throws EncryptedDocumentException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @return value present in the specified file, sheet name, row and cell
	 */
	public String getExcelValue(String excelFile, String SheetName, int row, int cell)
			throws EncryptedDocumentException, FileNotFoundException, IOException, InvalidFormatException {

		wb = WorkbookFactory.create(new FileInputStream("./data/Test.xlsx"));
		String value = wb.getSheet(SheetName).getRow(row).getCell(cell).toString();
		return value;
	}

	/**
	 * This method is used to set value in specified excel file
	 * 
	 * @param excelFile take excel file path
	 * @param SheetName take Sheet name in String format
	 * @param row       take row no in int format
	 * @param cell      take cell no in int format
	 * @param value     take value to be set in excel file in String format.
	 * @throws EncryptedDocumentException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public void setExcelValue(String excelFile, String SheetName, int row, int cell, String value)
			throws EncryptedDocumentException, FileNotFoundException, IOException, InvalidFormatException {
		wb = WorkbookFactory.create(new FileInputStream(new File("./data/Test.xlsx")));

		if (wb.getSheet(SheetName) == null) {
			wb.createSheet(SheetName);
		}
		if (wb.getSheet(SheetName).getRow(row) == null) {
			wb.getSheet(SheetName).createRow(row);

		}

		wb.getSheet(SheetName).getRow(row).createCell(cell).setCellValue(value);
		wb.write(new FileOutputStream(new File("./data/Test.xlsx")));
	}
}