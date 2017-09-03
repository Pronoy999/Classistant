package com.project.classistant;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Created by pronoymukherjee on 02/09/17.
 */

public class ExcelCreator {
    public void createExcel(){
        try {
            WritableWorkbook workbook = Workbook.createWorkbook(new File(Constant.EXCEL_FILE_NAME));
            WritableSheet sheet=workbook.createSheet(Constant.EXCEL_SHEET_NAME,0);
            
        }
        catch (IOException e){
            Message.logMessages("ERROR: ",e.toString());
        }
    }
}
