


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;

/**
 * Created by hth on 2017/9/2.
 */
public class POI {

    public static void main(String[] args) throws IOException, InvalidFormatException {
        // 创建 Excel 文件的输入流对象
//        FileInputStream excelFileInputStream = new FileInputStream("/Users/hth/Documents/诺基亚工作/数据质量报告/HTTP话单数_采集hdfs核查_20170905.xlsx");
        InputStream inp = new FileInputStream("/Users/hth/Documents/诺基亚工作/数据质量报告/HTTP话单数_采集hdfs核查_20170908.xlsx");
        // XSSFWorkbook 就代表一个 Excel 文件
// 创建其对象，就打开这个 Excel 文件
//        XSSFWorkbook workbook = new XSSFWorkbook(excelFileInputStream);
// 输入流使用后，及时关闭！这是文件流操作中极好的一个习惯！
        Workbook wb = WorkbookFactory.create(inp);
        Sheet sheet = wb.getSheetAt(2);
        Row row = sheet.getRow(6);
        Cell cell = row.getCell(3);
        System.out.println(cell.getStringCellValue());
//        excelFileInputStream.close();
    }
}
