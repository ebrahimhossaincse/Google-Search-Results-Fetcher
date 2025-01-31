package me.hossain.ebrahim.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ExcelUtils {

    private static final String EXCEL_FILE_PATH = "./testdata/data.xlsx";  // Modify this path

    // Reads values from the specified sheet by name and column position in the Excel file
    public static List<String> readValuesFromExcel(String sheetName, int columnPosition) throws Exception {
        List<String> values = new ArrayList<>();
        FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH);
        Workbook workbook = new XSSFWorkbook(fis);

        Sheet sheet = workbook.getSheet(sheetName);  // Get sheet by name

        // Loop through all rows to collect values from the specified column position
        for (Row row : sheet) {
            Cell cell = row.getCell(columnPosition);
            if (cell != null) {
                values.add(cell.getStringCellValue());
            }
        }

        fis.close();
        workbook.close();
        return values;
    }

    // Writes the shortest and longest results to the specified row
    public static void writeResultsToExcel(String sheetName, int rowNum, String longest, String shortest) throws Exception {
        FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName); // Get the specific sheet by name

        if (sheet == null) {
            sheet = workbook.createSheet(sheetName); // Create the sheet if it doesn't exist
        }

        Row row = sheet.getRow(rowNum); // Get the row where the results should be written
        if (row == null) {
            row = sheet.createRow(rowNum); // Create the row if it doesn't exist
        }

        row.createCell(4).setCellValue(longest);  // Write Longest Option to column E (index 4)
        row.createCell(5).setCellValue(shortest); // Write Shortest Option to column F (index 5)

        fis.close();

        // Save the workbook back to the file
        FileOutputStream fos = new FileOutputStream(EXCEL_FILE_PATH);
        workbook.write(fos);
        fos.close();
        workbook.close();
    }
}
