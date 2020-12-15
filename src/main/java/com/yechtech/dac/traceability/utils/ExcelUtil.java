package com.yechtech.dac.traceability.utils;

import com.yechtech.dac.traceability.domain.BatchTraceabilityMasterData;
import org.apache.poi.hssf.usermodel.*;

import java.text.SimpleDateFormat;
import java.util.List;

public class ExcelUtil {
	/**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param wb HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, List<String> title, List list, HSSFWorkbook wb, String type) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成一个表格，设置表格名称为"学生表"
        HSSFSheet sheet = workbook.createSheet(sheetName);

        //设置表格列宽度为10个字节
        sheet.setDefaultColumnWidth(10);
        //创建第一行表头
        HSSFRow headrow = sheet.createRow(0);
        //遍历添加表头(下面模拟遍历学生，也是同样的操作过程)
        for (int i = 0; i < title.size(); i++) {
            //创建一个单元格
            HSSFCell cell = headrow.createCell(i);
            //创建一个内容对象
            HSSFRichTextString text = new HSSFRichTextString(title.get(i));
            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
        }

        //创建内容
        for (int i = 0; i < list.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);
            //将内容按顺序赋给对应的列对象
            BatchTraceabilityMasterData data = (BatchTraceabilityMasterData) list.get(i);
            row.createCell(0).setCellValue(data.getSkuName());
            row.createCell(1).setCellValue(data.getSupplierName());
            row.createCell(2).setCellValue(data.getManufacturerName());
            row.createCell(3).setCellValue(sdf.format(data.getProductionDate()));
            row.createCell(4).setCellValue(data.getProductionQty());
            row.createCell(5).setCellValue(data.getUom());
            row.createCell(6).setCellValue(data.getSupplierShipmentQty());
            row.createCell(7).setCellValue(data.getReceivedQty());
            row.createCell(8).setCellValue(data.getShipmentQty());
            row.createCell(9).setCellValue(data.getInventoriesQty());
            if(null != data.getTraceRatio()){
                row.createCell(10).setCellValue(data.getTraceRatio().toString());
            }
            row.createCell(11).setCellValue(data.getSkuJdecode());
            row.createCell(12).setCellValue(data.getSupplierJdecode());
        }
        return workbook;
    }
}
