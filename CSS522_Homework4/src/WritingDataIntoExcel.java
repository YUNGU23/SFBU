import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class WritingDataIntoExcel {

    public static void main(String[] args) throws IOException {

        FileOutputStream file = new FileOutputStream("D:\\gu\\projects\\SFBU\\CSS522_Homework4\\src\\LoginData1.xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet1 = workbook.createSheet("Dataset1");
        XSSFSheet sheet2 = workbook.createSheet("Dataset2");

        for (int i = 0; i <=5; i++ ) {
            XSSFRow row1 = sheet1.createRow(i);
            XSSFRow row2 = sheet2.createRow(i);

            for (int j =0; j < 3; j++) {
                row1.createCell(j).setCellValue("abc");
                row2.createCell(j).setCellValue("xyz");
            }
        }

        workbook.write(file);
        file.close();

        System.out.println(" written data into excel sheet is completed.");

    }
}
