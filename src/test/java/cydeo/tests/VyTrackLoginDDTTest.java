package cydeo.tests;

import cydeo.pages.VyTrackDashboardPage;
import cydeo.pages.VyTrackLoginPage;
import cydeo.utilities.ConfigurationReader;
import cydeo.utilities.Driver;
import io.cucumber.java.After;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;

public class VyTrackLoginDDTTest {

    VyTrackLoginPage loginPage = new VyTrackLoginPage();
    VyTrackDashboardPage dashboardPage =new VyTrackDashboardPage();

    @Before
    public void setUp(){
        Driver.getDriver().get(ConfigurationReader.getProperty("vytrack.url"));
    }

    @After
    public void tearDown(){
Driver.closeDriver();
    }
    @Test
    public void loginDDTTest() throws IOException {

        String path="VyTrack.xlsx";
        File file =new File(path);


        FileInputStream fileInputStream=new FileInputStream(file);
        XSSFWorkbook workbook=new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet =workbook.getSheet("data");

        for (int i=1; i<sheet.getLastRowNum(); i++){

            String username=sheet.getRow(i).getCell(0).toString();
            String password=sheet.getRow(i).getCell(1).toString();
            String firstName=sheet.getRow(i).getCell(2).toString();
            String lastName=sheet.getRow(i).getCell(3).toString();

     loginPage.login(username,password);


            WebDriverWait wait =new WebDriverWait(Driver.getDriver(),30);
            WebElement loaderMask=Driver.getDriver().findElement(By.cssSelector("div[class='loader-mask shown']"));
            wait.until(ExpectedConditions.invisibilityOf(loaderMask));

            String actualFullName=dashboardPage.fullName.getText();

            //Getting result

            XSSFCell resultCell=sheet.getRow(i).getCell(4);

            if (actualFullName.contains(firstName) && actualFullName.contains(lastName)){

                System.out.println("PASS");
                resultCell.setCellValue("PASS");
            }else {
                System.out.println("FAIL");
                resultCell.setCellValue("FAIL");
            }
            dashboardPage.logout();
        }

        FileOutputStream out = new FileOutputStream(path);
        workbook.write(out);

        fileInputStream.close();
        out.close();
        workbook.close();


    }


}
