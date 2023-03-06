package cydeo.tests;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelRead {

@Test
    public void read_from_excel_file() throws IOException{
    String path = "SampleData.xlsx";

    File file =new File(path);


    //to read from excel we need to load it to FileInputStream
  FileInputStream fileInputStream=new FileInputStream(file);

    //1- Create a workbook
    XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
    //2- Create a sheet from currently opened workbook
XSSFSheet sheet = workbook.getSheet("Employees");
    //3- Select row and cell
    //print out mary's cell
    // Indexes start from 0

    System.out.println(sheet.getRow(1).getCell(0));


    //print out developer
    System.out.println(sheet.getRow(3).getCell(2));

    System.out.println(sheet.getPhysicalNumberOfRows());



    int usedRows=sheet.getPhysicalNumberOfRows();

    //TODO: Create a logic to print out Linda's Job_ID
    //Chexk if name is Linda ===> print out Linda's Job_ID

    for (int rowNum=0; rowNum<usedRows; rowNum++){
        if (sheet.getRow(rowNum).getCell(0).toString().equals("Linda")){

            System.out.println(sheet.getRow(rowNum).getCell(2));
        }

    }
    workbook.close();

}

}

