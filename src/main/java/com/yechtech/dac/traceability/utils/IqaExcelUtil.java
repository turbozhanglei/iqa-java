package com.yechtech.dac.traceability.utils;

import com.yechtech.dac.traceability.dao.BatchTraceabilityDetailedSummaryMapper;
import com.yechtech.dac.traceability.domain.*;
import com.yechtech.dac.traceability.dto.BatchTraceabilityMasterDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class IqaExcelUtil {

	/**
     * 导出Excel
     * @param
     * @param
     * @param sumList sheet名称
     * @param materialList
     * @param productList
     * @param integrationList
     * @param logisticsList
     * @param resList
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(List<DetailedSummary> sumList,
                                               List<BatchTraceabilityRmi> materialList,
                                               List<BatchTraceabilityManufacturer> productList,
                                               List<BatchTraceabilityCc> integrationList,
                                               List<BatchTraceabilityLc> logisticsList,
                                               List<BatchTraceabilityStore> resList
                                               ) {
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(2);
        List<String> sumTitle=new ArrayList<>();
        sumTitle.add("品项名称");
        sumTitle.add("供应商名称");
        sumTitle.add("生产商名称");
        sumTitle.add("生产日期");
        sumTitle.add("生产量");
        sumTitle.add("采购单位");
        sumTitle.add("供应商发货数量");
        sumTitle.add("整合中心个数");
        sumTitle.add("整合中心到货数量");
        sumTitle.add("整合中心库存数量");
        sumTitle.add("物流中心个数");
        sumTitle.add("物流中心到货数量");
        sumTitle.add("物流中心库存数量");
        sumTitle.add("FQA市场个数");
        sumTitle.add("餐厅个数");
        sumTitle.add("品项JDE Code");
        sumTitle.add("供应商JDE Code");
        sumTitle.add("生产商EQA Code");
        sumTitle.add("追溯率");

        List<String> materialTitle=new ArrayList<>();
        materialTitle.add("原料名称");
        materialTitle.add("原料生产商名称");
        materialTitle.add("原料批次号");
        materialTitle.add("投料时间");
        materialTitle.add("投料量");
        materialTitle.add("单位");

        List<String> productTitle=new ArrayList<>();
        productTitle.add("生产商名称");
        productTitle.add("发货时间");
        productTitle.add("发货数量");
        productTitle.add("发货至");

        List<String> integrationTitle=new ArrayList<>();
        integrationTitle.add("整合中心Code");
        integrationTitle.add("整合中心名称");
        integrationTitle.add("仓库类型");
        integrationTitle.add("最早到货日期");
        integrationTitle.add("到货数量");
        integrationTitle.add("库存数量");
        integrationTitle.add("出货至");
        integrationTitle.add("出货数量");
        integrationTitle.add("采购单位");


        List<String> logisticsTitle=new ArrayList<>();
        logisticsTitle.add("整合中心Code");
        logisticsTitle.add("整合中心名称");
        logisticsTitle.add("物流中心Code");
        logisticsTitle.add("物流中心名称");
        logisticsTitle.add("仓库类型");
        logisticsTitle.add("最早到货日期");
        logisticsTitle.add("到货数量");
        logisticsTitle.add("库存数量");
        logisticsTitle.add("最晚出货日期");
        logisticsTitle.add("出货数量");
        logisticsTitle.add("采购单位");



        List<String> resTitle=new ArrayList<>();
        resTitle.add("餐厅编码");
        resTitle.add("餐厅名称");
        resTitle.add("品牌");
        resTitle.add("FQA市场");
        resTitle.add("FQA市场Code");
        resTitle.add("最早到货日期");
        resTitle.add("到货数量");
        resTitle.add("最晚到货日期");
        resTitle.add("来源LC编码");
        resTitle.add("来源LC名称");
        resTitle.add("采购单位");


        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成多个表格
        HSSFSheet sumSheet = workbook.createSheet("汇总明细");
        HSSFSheet materialSheet = workbook.createSheet("上游原料信息");
        HSSFSheet productSheet = workbook.createSheet("生产商信息");
        HSSFSheet integrationSheet = workbook.createSheet("整合中心信息");
        HSSFSheet logisticsSheet = workbook.createSheet("物流中心");
        HSSFSheet resSheet = workbook.createSheet("餐厅信息");


        //设置表格列宽度为10个字节
        sumSheet.setDefaultColumnWidth(10);
        materialSheet.setDefaultColumnWidth(10);
        productSheet.setDefaultColumnWidth(10);
        integrationSheet.setDefaultColumnWidth(10);
        logisticsSheet.setDefaultColumnWidth(10);
        resSheet.setDefaultColumnWidth(10);
        //创建第一行表头
        HSSFRow sumHeadrow = sumSheet.createRow(0);
        HSSFRow materialHeadrow = materialSheet.createRow(0);
        HSSFRow productHeadrow = productSheet.createRow(0);
        HSSFRow integrationHeadrow = integrationSheet.createRow(0);
        HSSFRow logisticsHeadrow = logisticsSheet.createRow(0);
        HSSFRow resHeadrow = resSheet.createRow(0);
        //遍历添加表头 -汇总信息
        for (int i = 0; i < sumTitle.size(); i++) {
            //创建一个单元格
            HSSFCell cell = sumHeadrow.createCell(i);
            //创建一个内容对象
            HSSFRichTextString text = new HSSFRichTextString(sumTitle.get(i));
            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
        }
        //上游原料
        for (int i = 0; i < materialTitle.size(); i++) {
            //创建一个单元格
            HSSFCell cell = materialHeadrow.createCell(i);
            //创建一个内容对象
            HSSFRichTextString text = new HSSFRichTextString(materialTitle.get(i));
            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
        }
        //生产商信息
        for (int i = 0; i < productTitle.size(); i++) {
            //创建一个单元格
            HSSFCell cell = productHeadrow.createCell(i);
            //创建一个内容对象
            HSSFRichTextString text = new HSSFRichTextString(productTitle.get(i));
            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
        }
        //整合中心
        for (int i = 0; i < integrationTitle.size(); i++) {
            //创建一个单元格
            HSSFCell cell = integrationHeadrow.createCell(i);
            //创建一个内容对象
            HSSFRichTextString text = new HSSFRichTextString(integrationTitle.get(i));
            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
        }
        //物流中心
        for (int i = 0; i < logisticsTitle.size(); i++) {
            //创建一个单元格
            HSSFCell cell = logisticsHeadrow.createCell(i);
            //创建一个内容对象
            HSSFRichTextString text = new HSSFRichTextString(logisticsTitle.get(i));
            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
        }
        //餐厅
        for (int i = 0; i < resTitle.size(); i++) {
            //创建一个单元格
            HSSFCell cell = resHeadrow.createCell(i);
            //创建一个内容对象
            HSSFRichTextString text = new HSSFRichTextString(resTitle.get(i));
            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
        }

        if (CollectionUtils.isNotEmpty(sumList)){
            //创建内容-明细
            for (int i = 0; i < sumList.size(); i++) {
                HSSFRow row = sumSheet.createRow(i + 1);
                //将内容按顺序赋给对应的列对象
                DetailedSummary data = sumList.get(i);
                row.createCell(0).setCellValue(data.getSkuName());
                row.createCell(1).setCellValue(data.getSupplierName());
                row.createCell(2).setCellValue(data.getManufacturerName());//缺一个生产商名称
                if (null !=data.getProductionDate()){
                    row.createCell(3).setCellValue(sdf.format(data.getProductionDate()));
                }
                if(null !=data.getProductionQty()){
                    row.createCell(4).setCellValue(data.getProductionQty());
                }
                row.createCell(5).setCellValue(data.getUom());
                if (null !=data.getSupplierShipmentQty()){
                    row.createCell(6).setCellValue(data.getSupplierShipmentQty());
                }
                row.createCell(7).setCellValue(data.getCcNum());
                if (null !=data.getCcReceivedQty()){
                    row.createCell(8).setCellValue(data.getCcReceivedQty().toString());
                }
                if (null !=data.getCcInventoriesQty()){
                    row.createCell(9).setCellValue(data.getCcInventoriesQty().toString());
                }
                if(null !=data.getLcNum()){
                    row.createCell(10).setCellValue(data.getLcNum());
                }
                if (null !=data.getLcReceivedQty()){
                    row.createCell(11).setCellValue(data.getLcReceivedQty().toString());
                }
                if (null !=data.getCcInventoriesQty()){
                    row.createCell(12).setCellValue(data.getLcInventoriesQty().toString());
                }
                if (null !=data.getFqaNum()){
                    row.createCell(13).setCellValue(data.getFqaNum());
                }
                if (null !=data.getStorNum()){
                    row.createCell(14).setCellValue(data.getStorNum());
                }
                row.createCell(15).setCellValue(data.getSkuJdecode());
                row.createCell(16).setCellValue(data.getSupplierJdecode());
                row.createCell(17).setCellValue(data.getManufacturerEqacode());
                if (null !=data.getTraceRatio()){
                    row.createCell(18).setCellValue(percent.format(data.getTraceRatio().setScale(4, BigDecimal.ROUND_HALF_UP)));
                }else {
                    row.createCell(18).setCellValue("0.00%");
                }

            }
        }
        if (CollectionUtils.isNotEmpty(materialList)){
            //创建内容-原料
            for (int i = 0; i < materialList.size(); i++) {
                HSSFRow row = materialSheet.createRow(i + 1);
                //将内容按顺序赋给对应的列对象
                BatchTraceabilityRmi data = materialList.get(i);
                row.createCell(0).setCellValue(data.getRmiSkuName());
                row.createCell(1).setCellValue(data.getManufacturerRmiName());
                row.createCell(2).setCellValue(data.getRmiBatchNo());
                if (null !=data.getProductionDate()){
                    row.createCell(3).setCellValue(sdf.format(data.getProductionDate()));
                }
                if (null !=data.getQuantity()){
                    row.createCell(4).setCellValue(data.getQuantity());
                }
                row.createCell(5).setCellValue(data.getUom());
            }
        }

        if (CollectionUtils.isNotEmpty(productList)){
            //创建内容-生产商
            for (int i = 0; i < productList.size(); i++) {
                HSSFRow row = productSheet.createRow(i + 1);
                //将内容按顺序赋给对应的列对象
                BatchTraceabilityManufacturer data = productList.get(i);
                row.createCell(0).setCellValue(data.getManufacturerName());
//                row.createCell(1).setCellValue(data.getProductionQuantity());去除生产量
                if (null !=data.getShipDate()){
                    row.createCell(1).setCellValue(sdf.format(data.getShipDate()));
                }
                if (null !=data.getShipmentQuantity()){
                    row.createCell(2).setCellValue(data.getShipmentQuantity());
                }
                row.createCell(3).setCellValue(data.getReceiver());
            }
        }

        if (CollectionUtils.isNotEmpty(integrationList)){
            //创建内容-整合中心
            for (int i = 0; i < integrationList.size(); i++) {
                HSSFRow row = integrationSheet.createRow(i + 1);
                //将内容按顺序赋给对应的列对象
                BatchTraceabilityCc data = integrationList.get(i);
                row.createCell(0).setCellValue(data.getSlcCodeCc());
                row.createCell(1).setCellValue(data.getSlcNameCc());
                row.createCell(2).setCellValue(data.getWarehouseCategory());
                if (null !=data.getEarliestdelvDate()){
                    row.createCell(3).setCellValue(sdf.format(data.getEarliestdelvDate()));
                }
                if (null !=data.getReceivedQty()){
                    row.createCell(4).setCellValue(data.getReceivedQty().toString());
                }
                if (null !=data.getInventoriesQty()){
                    row.createCell(5).setCellValue(data.getInventoriesQty().toString());
                }
                row.createCell(6).setCellValue(data.getReceiver());
                if (null !=data.getShipmentQty()){
                    row.createCell(7).setCellValue(data.getShipmentQty().toString());
                }
                row.createCell(8).setCellValue(data.getUom());
            }
        }
        if (CollectionUtils.isNotEmpty(logisticsList)){
            //创建内容-物流中心
            for (int i = 0; i < logisticsList.size(); i++) {
                HSSFRow row = logisticsSheet.createRow(i + 1);
                //将内容按顺序赋给对应的列对象
                BatchTraceabilityLc data = logisticsList.get(i);
                row.createCell(0).setCellValue(data.getSlcCodeCc());
                row.createCell(1).setCellValue(data.getSlcNameCc());
                row.createCell(2).setCellValue(data.getSlcCodeLc());
                row.createCell(3).setCellValue(data.getSlcNameLc());
                row.createCell(4).setCellValue(data.getWarehouseCategory());
                if (null !=data.getEarliestdelvdate()){
                    row.createCell(5).setCellValue(sdf.format(data.getEarliestdelvdate()));
                }

                if (null !=data.getReceivedQty()){
                    row.createCell(6).setCellValue(data.getReceivedQty().toString());
                }
                if (null !=data.getInventoriesQty()){
                    row.createCell(7).setCellValue(data.getInventoriesQty().toString());
                }
                if (null !=data.getLatestshipdate()){
                    row.createCell(8).setCellValue(sdf.format(data.getLatestshipdate()));
                }
                if (null !=data.getShipmentQty()){
                    row.createCell(9).setCellValue(data.getShipmentQty().toString());
                }
                row.createCell(10).setCellValue(data.getUom());

            }
        }

        if (CollectionUtils.isNotEmpty(resList)){
            //创建内容-餐厅
            for (int i = 0; i < resList.size(); i++) {
                HSSFRow row = resSheet.createRow(i + 1);
                //将内容按顺序赋给对应的列对象
                BatchTraceabilityStore data = resList.get(i);
                row.createCell(0).setCellValue(data.getStoreCode());
                row.createCell(1).setCellValue(data.getStoreName());
                row.createCell(2).setCellValue(data.getBrandNameCn());
                row.createCell(3).setCellValue(data.getFqaMarketname());
                row.createCell(4).setCellValue(data.getFqaMarketcode());
                if (null !=data.getEarliestdelvdate()){
                    row.createCell(5).setCellValue(sdf.format(data.getEarliestdelvdate()));
                }
                if (null !=data.getQuantity()){
                    row.createCell(6).setCellValue(data.getQuantity().toString());
                }
                if (null !=data.getLatestdelvdate()){
                    row.createCell(7).setCellValue(sdf.format(data.getLatestdelvdate()));
                }
                row.createCell(8).setCellValue(data.getSlcCodeLc());
                row.createCell(9).setCellValue(data.getSlcNameLc());
                row.createCell(10).setCellValue(data.getUom());
            }
        }
        return workbook;
    }
}
