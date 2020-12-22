package com.yechtech.dac.traceability.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yechtech.dac.common.domain.DacResponse;
import com.yechtech.dac.common.service.RedisService;
import com.yechtech.dac.traceability.dao.*;
import com.yechtech.dac.traceability.domain.*;
import com.yechtech.dac.traceability.dto.BatchTraceabilityDto;
import com.yechtech.dac.traceability.dto.BatchTraceabilityMasterDto;
import com.yechtech.dac.traceability.service.ExcelService;
import com.yechtech.dac.traceability.utils.IqaExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author:Turbozhang
 * @createTime:2020/12/12 18:13
 * @version:1.0
 */
@Service
@Slf4j
public class ExcelServiceImpl implements ExcelService {

    @Resource
    BatchTraceabilityDetailedSummaryMapper detailedSummaryMapper;

    @Resource
    BatchTraceabilityRmiMapper rmiMapper;

    @Resource
    BatchTraceabilityCcMapper ccMapper;

    @Resource
    BatchTraceabilityLcMapper batchTraceabilityLcMapper;

    @Resource
    BatchTraceabilityStoreMapper storeMapper;

    @Resource
    BatchTraceabilityManufacturerMapper manufacturerMapper;

    @Resource
    BatchTraceabilityMasterDataMapper masterDataMapper;



    @Value("${spring.profiles.active}")
    private String env;

    @Override
    public HSSFWorkbook getHSSFWorkbook(BatchTraceabilityDto batchDto ,String psid) {
        String manufacturerName ="";
        QueryWrapper<DetailedSummary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("supplier_jdecode",batchDto.getSupplierJdecode());
        queryWrapper.eq("sku_jdecode",batchDto.getSkuJdecode());
        queryWrapper.eq("production_date",batchDto.getProductionDate());
        List<DetailedSummary> detailedSummaries = detailedSummaryMapper.selectList(queryWrapper);
        BatchTraceabilityMasterData query =new BatchTraceabilityMasterData();
        query.setSupplierJdecode(batchDto.getSupplierJdecode());
        query.setSkuJdecode(batchDto.getSkuJdecode());
        query.setProductionDate(batchDto.getProductionDate());
        //查询生产商名称
        List<BatchTraceabilityMasterData> result = masterDataMapper.query(query);
        if(CollectionUtils.isNotEmpty(result)){
            manufacturerName =result.get(0).getManufacturerName();
        }


        //获取详情页状态 -上游原料
        BatchTraceabilityRmi queryRmi = new BatchTraceabilityRmi();
        queryRmi.setManufacturerEqaCode(batchDto.getManufacturerEqaCode());
        queryRmi.setSkuJdecode(batchDto.getSkuJdecode());
        queryRmi.setProductionDate(batchDto.getProductionDate());
        queryRmi.setPsid(psid);

        List<BatchTraceabilityRmi> batchTraceabilityRmis = rmiMapper.queryList(queryRmi);
        log.error("上游原料返回参数,batchTraceabilityRmis=========》",batchTraceabilityRmis.toString());

        //获取生产商
        BatchTraceabilityManufacturer manufacturer =new BatchTraceabilityManufacturer();
        manufacturer.setManufacturerEqacode(batchDto.getManufacturerEqaCode());
        manufacturer.setSkuJdecode(batchDto.getSkuJdecode());
        manufacturer.setProductionDate(batchDto.getProductionDate());
        manufacturer.setPsid(psid);
        List<BatchTraceabilityManufacturer> batchTraceabilityManufacturer = manufacturerMapper.queryList(manufacturer);

        //获取整合中心
        BatchTraceabilityCc bilityCc =new BatchTraceabilityCc();
        bilityCc.setSupplierJdecode(batchDto.getSupplierJdecode());
        bilityCc.setSkuJdecode(batchDto.getSkuJdecode());
        bilityCc.setProductionDate(batchDto.getProductionDate());
        bilityCc.setPsid(psid);
        List<BatchTraceabilityCc> batchTraceabilityCc = ccMapper.queryList(bilityCc);

        //获取物流中心
        BatchTraceabilityLc traceabilityLc =new BatchTraceabilityLc();
        traceabilityLc.setSupplierJdecode(batchDto.getSupplierJdecode());
        traceabilityLc.setSkuJdecode(batchDto.getSkuJdecode());
        traceabilityLc.setProductionDate(batchDto.getProductionDate());
        traceabilityLc.setPsid(psid);
        List<BatchTraceabilityLc> batchTraceabilityLc = batchTraceabilityLcMapper.queryList(traceabilityLc);

        //获取餐厅中心
        BatchTraceabilityStore traceabilityStore =new BatchTraceabilityStore();
        traceabilityStore.setSupplierJdecode(batchDto.getSupplierJdecode());
        traceabilityStore.setSkuJdecode(batchDto.getSkuJdecode());
        traceabilityStore.setProductionDate(batchDto.getProductionDate());
        traceabilityStore.setPsid(psid);
        List<BatchTraceabilityStore> batchTraceabilityStores = storeMapper.queryList(traceabilityStore);

        HSSFWorkbook wb = IqaExcelUtil.getHSSFWorkbook(
                detailedSummaries,
                batchTraceabilityRmis,
                batchTraceabilityManufacturer,
                batchTraceabilityCc,
                batchTraceabilityLc,
                batchTraceabilityStores,
                manufacturerName);
        return wb;
    }


}
