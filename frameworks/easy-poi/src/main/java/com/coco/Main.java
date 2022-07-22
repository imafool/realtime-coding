package com.coco;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.coco.entity.People;
import com.coco.exports.ExportHandle;
import com.coco.imports.ImportHandler;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        // export();
        importData();
    }

    private static void importData() {
        ImportParams importParams = new ImportParams();
        ImportHandler importHandler = new ImportHandler();
        importParams.setImportFields(new String[]{"年龄", "生日", "姓名", "性别"});
        importParams.setDataHandler(importHandler);

        try {
            ExcelImportResult<People> result = ExcelImportUtil.importExcelMore(new FileInputStream("C:\\Users\\Administrator\\Desktop\\app.xlsx"), People.class, importParams);
            List<People> list = result.getList();
            if (list.size() == 0){
                System.out.println("空表");
            }
            for (People p : list){
                System.out.println("------------------------------");
                System.out.println(p);
            }
        } catch (Exception e) {
            System.out.println("解析失败");
        }
    }

    private static void export() throws Exception {
        ExportParams exportParams = new ExportParams(null, "人员表", ExcelType.XSSF);
        Workbook workbook = null;

        People people1 = new People();
        people1.setAge(12);
        people1.setBirthDay("12月5号");
        people1.setName("小明");
        People people2 = new People();
        people2.setAge(18);
        people2.setBirthDay("12月9号");
        people2.setName("小宏");
        people2.setSex("女");
        List<People> list = Lists.newArrayList(people1, people2);

        try {
            workbook = ExcelExportUtil.exportBigExcel(exportParams, People.class, list);
        } finally {
            if (workbook != null){
                //此处必须要关闭，否则会导致stream closed异常
                ExcelExportUtil.closeExportBigExcel();
            }
        }
        try(ByteArrayOutputStream os = new ByteArrayOutputStream(1024)) {
            workbook.write(os);
            ExportHandle.write(os);
        } catch (IOException e) {
            throw new Exception("导出报表失败");
        }
    }
}
