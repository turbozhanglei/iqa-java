package com.yechtech.dac.traceability.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.yechtech.dac.common.domain.DacResponse;
import com.yechtech.dac.common.service.RedisService;
import com.yechtech.dac.common.utils.HttpClientUtils;
import com.yechtech.dac.traceability.dao.*;
import com.yechtech.dac.traceability.domain.*;
import com.yechtech.dac.traceability.dto.*;
import com.yechtech.dac.traceability.enums.StatusType;
import com.yechtech.dac.traceability.service.BatchTraceablilityBaseService;
import com.yechtech.dac.traceability.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description :
 * @author : Turbozhang
 * @createTime : 2020/11/11 14:45
 * @version : 1.0
 */
@Service
@Slf4j
public class BatchTraceablilityBaseServiceImpl implements BatchTraceablilityBaseService {

    @Value("${spring.profiles.active}")
    private String env;

    @Resource
    BatchTraceabilityMasterDataMapper masterDataMapper;

    @Resource
    BatchTraceabilityManufacturerMapper manufacturerMapper;

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

    @Autowired
    private RedisService redisService;

    @Resource
    ConfigMapper configMapper;

    @Resource
    EdwDiskuMapper edwDiskuMapper;

    @Resource
    EdwDimanufacturerMapper edwDimanufacturerMapper;

    @Resource
    EdwIqaMasterDataRmiMapper edwIqaMasterDataRmiMapper;

    @Resource
    EdwDistoreMapper edwDistoreMapper;


    @Override
    public DacResponse
    selectPage(BatchTraceabilityMasterDto batchDto) {
        BatchTraceabilityMasterData batchTraceabilityMasterData=new BatchTraceabilityMasterData();
        if (env.equals("prd") || env.equals("uat")){
            String bloo = checkToken(batchDto.getTokenIqa());
            if (StringUtils.isEmpty(bloo)){
                return new DacResponse().message("用户信息已失效");
            }
            batchTraceabilityMasterData.setPsid(bloo);
        }
//        QueryWrapper<BatchTraceabilityMasterData> queryWrapper = getMyApplyDtoQueryWrapper(batchDto);
//        Page<BatchTraceabilityMasterData> page = new Page<>(batchDto.getPageNum(), batchDto.getPageSize());
//        IPage<BatchTraceabilityMasterData> batchTraceabilityMasterDataIPage = masterDataMapper.selectPage(page,queryWrapper);

        BeanUtils.copyProperties(batchDto,batchTraceabilityMasterData);
        int startIndex = (batchDto.getPageNum() - 1) * batchDto.getPageSize();
        List<BatchTraceabilityMasterData> list = masterDataMapper.queryPage(startIndex, batchDto.getPageSize(), batchTraceabilityMasterData);
        long count = masterDataMapper.queryPageCount(batchTraceabilityMasterData);
        PageResult pageResult = new PageResult();
        pageResult.setData(list);
        pageResult.setTotal(count);
        pageResult.setMessage("Success");
        return new DacResponse().data(pageResult);
    }

    @Override
    public DacResponse selectInputList(FilterConditionDto batchDto){
        String psid="";
        if (env.equals("prd") || env.equals("uat")){
            String bloo = checkToken(batchDto.getTokenIqa());
            if (StringUtils.isEmpty(bloo)){
                return new DacResponse().message("用户信息已失效");
            }
            psid = bloo;
        }
        List<FilterConditionData> responseList  = new ArrayList<>();
        switch (batchDto.getType()){
            case 1:
                //品项名称
//                BatchTraceabilityManufacturer manufacturerDto =new BatchTraceabilityManufacturer();
//                manufacturerDto.setSkuName(batchDto.getSkuName());
//                manufacturerDto.setPsid(psid);
//                List<BatchTraceabilityManufacturer> resultList = manufacturerMapper.query(manufacturerDto);
                EdwDisku edwDisku =new EdwDisku();
                edwDisku.setSkuName(batchDto.getSkuName());
                edwDisku.setPsid(psid);
                List<EdwDisku> resultList = edwDiskuMapper.query(edwDisku);
                resultList.forEach(item->{
                    FilterConditionData filterConditionData = new FilterConditionData();
                    filterConditionData.setLable(item.getId().toString());
                    filterConditionData.setValue(item.getSkuName());
                    responseList.add(filterConditionData);
                });
                break;
            case 2:
                //生产商名称
//                BatchTraceabilityManufacturer queyManufacturer =new BatchTraceabilityManufacturer();
//                queyManufacturer.setManufacturerName(batchDto.getManufacturerName());
//                queyManufacturer.setPsid(psid);
//                List<BatchTraceabilityManufacturer> resultManufacturerList = manufacturerMapper.query(queyManufacturer);
                EdwDimanufacturer edwDimanufacturer =new EdwDimanufacturer();
                edwDimanufacturer.setManufacturerCnName(batchDto.getManufacturerName());
                List<EdwDimanufacturer> resultManufacturerList = edwDimanufacturerMapper.query(edwDimanufacturer);
                resultManufacturerList.forEach(item->{
                    FilterConditionData filterConditionData = new FilterConditionData();
                    filterConditionData.setLable(item.getId().toString());
                    filterConditionData.setValue(item.getManufacturerCnName());
                    responseList.add(filterConditionData);
                });
                break;
            case 3:
                //原料名称
//                BatchTraceabilityRmi rmiDto = new BatchTraceabilityRmi();
//                rmiDto.setRmiSkuName(batchDto.getRmiSkuName());
//                rmiDto.setPsid(psid);
//                List<BatchTraceabilityRmi> resultRmi = rmiMapper.query(rmiDto);
                EdwIqaMasterDataRmi masterDataRmi = new EdwIqaMasterDataRmi();
                masterDataRmi.setDataType("1");
                masterDataRmi.setDataValue(batchDto.getRmiSkuName());
                List<EdwIqaMasterDataRmi> resultRmi = edwIqaMasterDataRmiMapper.query(masterDataRmi);
                resultRmi.forEach(item->{
                    FilterConditionData filterConditionData = new FilterConditionData();
                    filterConditionData.setLable(item.getId().toString());
                    filterConditionData.setValue(item.getDataValue());
                    responseList.add(filterConditionData);
                });

                break;
            case 4:
                //原料生产商名称
//                BatchTraceabilityRmi qrmiDto = new BatchTraceabilityRmi();
//                qrmiDto.setPsid(psid);
//                qrmiDto.setManufacturerRmiName(batchDto.getManufacturerRmiName());
//                List<BatchTraceabilityRmi> resultqrmi = rmiMapper.query(qrmiDto);
                EdwIqaMasterDataRmi edwIqaMasterDataRmi = new EdwIqaMasterDataRmi();
                edwIqaMasterDataRmi.setDataType("2");
                edwIqaMasterDataRmi.setDataValue(batchDto.getRmiSkuName());
                List<EdwIqaMasterDataRmi> resultqrmi = edwIqaMasterDataRmiMapper.query(edwIqaMasterDataRmi);
                resultqrmi.forEach(item->{
                    FilterConditionData filterConditionData = new FilterConditionData();
                    filterConditionData.setLable(item.getId().toString());
                    filterConditionData.setValue(item.getDataValue());
                    responseList.add(filterConditionData);
                });
                break;
            case 5:
//                BatchTraceabilityStore storePro = new BatchTraceabilityStore();
//                storePro.setPsid(psid);
//                storePro.setSkuName(batchDto.getProductName());
//                List<BatchTraceabilityStore> resultPro = storeMapper.query(storePro);
                EdwDisku edwDiskuPro =new EdwDisku();
                edwDiskuPro.setSkuName(batchDto.getProductName());
                edwDiskuPro.setPsid(psid);
                List<EdwDisku> resultPro = edwDiskuMapper.query(edwDiskuPro);
                resultPro.forEach(item->{
                    FilterConditionData filterConditionData = new FilterConditionData();
                    filterConditionData.setLable(item.getId().toString());
                    filterConditionData.setValue(item.getSkuName());
                    responseList.add(filterConditionData);
                });
                break;
            case 6:
//                BatchTraceabilityStore storeBrand = new BatchTraceabilityStore();
//                storeBrand.setPsid(psid);
//                storeBrand.setBrandNameCn(batchDto.getBrandNameCn());
//                List<BatchTraceabilityStore> resultBrand = storeMapper.query(storeBrand);
                EdwDistore edwDistore =new EdwDistore();
                edwDistore.setBrandNameCn(batchDto.getBrandNameCn());
                List<EdwDistore> resultBrand = edwDistoreMapper.query(edwDistore);
                resultBrand.forEach(item->{
                    FilterConditionData filterConditionData = new FilterConditionData();
//                    filterConditionData.setLable(item.getId().toString());
                    if (null != item){
                        if (StringUtils.isNotBlank(item.getBrandNameCn())){
                            filterConditionData.setValue(item.getBrandNameCn());
                        }
                    }
                    responseList.add(filterConditionData);
                });
                break;
            default:
                break;
        }
        return new DacResponse().data(responseList);
    }

    @Override
    public DacResponse selectDetail(BatchTraceabilityDto batchDto) {
        log.info("获取批次追溯详情入参batchDto=========》,{}", batchDto.toString());
        if (env.equals("prd") || env.equals("uat")){
            String bloo = checkToken(batchDto.getTokenIqa());
            if (StringUtils.isEmpty(bloo)){
                return new DacResponse().message("用户信息已失效");
            }
        }
        BatchTraceabilityDetailedSummary batchTraceabilityDetailedSummary =new BatchTraceabilityDetailedSummary();
        SimpleDateFormat format =new SimpleDateFormat();
        QueryWrapper<DetailedSummary> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("id",batchDto.getId());
        queryWrapper.eq("supplier_jdecode",batchDto.getSupplierJdecode());
        queryWrapper.eq("sku_jdecode",batchDto.getSkuJdecode());
        queryWrapper.eq("production_date",batchDto.getProductionDate());

        List<DetailedSummary> detailedSummaries = detailedSummaryMapper.selectList(queryWrapper);

        if (CollectionUtils.isNotEmpty(detailedSummaries)){
            BeanUtils.copyProperties(detailedSummaries.get(0),batchTraceabilityDetailedSummary);
        }

        //获取详情页状态 -上游原料
        BatchTraceabilityRmi queryRmi = new BatchTraceabilityRmi();
        queryRmi.setManufacturerEqaCode(batchDto.getManufacturerEqaCode());
        queryRmi.setSkuJdecode(batchDto.getSkuJdecode());
        queryRmi.setProductionDate(batchDto.getProductionDate());

        List<BatchTraceabilityRmi> batchTraceabilityRmis = rmiMapper.query(queryRmi);
        if (CollectionUtils.isEmpty(batchTraceabilityRmis)){
            batchTraceabilityDetailedSummary.setMaterialStatus(StatusType.Zero.getCode());
        }else {
            batchTraceabilityDetailedSummary.setMaterialStatus(StatusType.One.getCode());
        }

        //获取生产商状态
        BatchTraceabilityManufacturer manufacturer =new BatchTraceabilityManufacturer();
        manufacturer.setManufacturerEqacode(batchDto.getManufacturerEqaCode());
        manufacturer.setSkuJdecode(batchDto.getSkuJdecode());
        manufacturer.setProductionDate(batchDto.getProductionDate());
        List<BatchTraceabilityManufacturer> batchTraceabilityManufacturer = manufacturerMapper.query(manufacturer);
        if (CollectionUtils.isEmpty(batchTraceabilityManufacturer)){
            batchTraceabilityDetailedSummary.setProductStauts(StatusType.Zero.getCode());
        }else {
            batchTraceabilityDetailedSummary.setProductStauts(StatusType.One.getCode());
        }


        //获取整合中心状态
        BatchTraceabilityCc bilityCc =new BatchTraceabilityCc();
        bilityCc.setSupplierJdecode(batchDto.getSupplierJdecode());
        bilityCc.setSkuJdecode(batchDto.getSkuJdecode());
        bilityCc.setProductionDate(batchDto.getProductionDate());
        List<BatchTraceabilityCc> batchTraceabilityCc = ccMapper.query(bilityCc);
        if (CollectionUtils.isEmpty(batchTraceabilityCc)){
            batchTraceabilityDetailedSummary.setIntegrationStauts(StatusType.Zero.getCode());
        }else {
            batchTraceabilityDetailedSummary.setIntegrationStauts(StatusType.One.getCode());
        }

        //获取物流中心状态
        BatchTraceabilityLc traceabilityLc =new BatchTraceabilityLc();
        traceabilityLc.setSupplierJdecode(batchDto.getSupplierJdecode());
        traceabilityLc.setSkuJdecode(batchDto.getSkuJdecode());
        traceabilityLc.setProductionDate(batchDto.getProductionDate());
        List<BatchTraceabilityLc> batchTraceabilityLc = batchTraceabilityLcMapper.query(traceabilityLc);
        if (CollectionUtils.isEmpty(batchTraceabilityLc)){
            batchTraceabilityDetailedSummary.setLogisticsStauts(StatusType.Zero.getCode());
        }else {
            batchTraceabilityDetailedSummary.setLogisticsStauts(StatusType.One.getCode());
        }

        //获取餐厅中心状态
        BatchTraceabilityStore traceabilityStore =new BatchTraceabilityStore();
        traceabilityStore.setSupplierJdecode(batchDto.getSupplierJdecode());
        traceabilityStore.setSkuJdecode(batchDto.getSkuJdecode());
        traceabilityStore.setProductionDate(batchDto.getProductionDate());
        List<BatchTraceabilityStore> batchTraceabilityStores = storeMapper.query(traceabilityStore);
        if (CollectionUtils.isEmpty(batchTraceabilityStores)){
            batchTraceabilityDetailedSummary.setRestaurantStatus(StatusType.Zero.getCode());
        }else {
            batchTraceabilityDetailedSummary.setRestaurantStatus(StatusType.One.getCode());
        }

        return new DacResponse().data(batchTraceabilityDetailedSummary);
    }

    @Override
    public DacResponse getUpstreamRmi(BatchTraceabilityBaseDto batchDto) {
        log.info("获取上游原料信息入参batchDto=========》,{}", batchDto.toString());
        BatchTraceabilityRmi batchTraceabilityRmi =new BatchTraceabilityRmi();
        if (env.equals("prd") || env.equals("uat")){
            String bloo = checkToken(batchDto.getTokenIqa());
            if (StringUtils.isEmpty(bloo)){
                return new DacResponse().message("用户信息已失效");
            }
            batchTraceabilityRmi.setPsid(bloo);
        }
//        QueryWrapper<BatchTraceabilityRmi> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("manufacturer_eqa_code",batchDto.getManufacturerEqaCode());
//        queryWrapper.eq("sku_jde_code",batchDto.getSkuJdeCode());
//        queryWrapper.eq("production_date",batchDto.getProductionDate());
        batchTraceabilityRmi.setManufacturerEqaCode(batchDto.getManufacturerEqaCode());
        batchTraceabilityRmi.setSkuJdecode(batchDto.getSkuJdecode());
        batchTraceabilityRmi.setProductionDate(batchDto.getProductionDate());

//        Page<BatchTraceabilityRmi> page = new Page<>(batchDto.getPageNum(), batchDto.getPageSize());
//        IPage<BatchTraceabilityRmi> batchTraceabilityRmiIPage = rmiMapper.selectPage(page, queryWrapper);
        int startIndex = (batchDto.getPageNum() - 1) * batchDto.getPageSize();
        List<BatchTraceabilityRmi> list = rmiMapper.queryPage(startIndex, batchDto.getPageSize(), batchTraceabilityRmi);
        long count = rmiMapper.queryPageCount(batchTraceabilityRmi);
        PageResult pageResult = new PageResult();
        pageResult.setData(list);
        pageResult.setTotal(count);
        pageResult.setMessage("Success");
        return new DacResponse().data(pageResult);
    }

    @Override
    public DacResponse getManufacturerList(BatchTraceabilityBaseDto batchDto) {
        log.info("获取生产商信息列表入参batchDto=========》,{}", batchDto.toString());
        BatchTraceabilityManufacturer batchTraceabilityManufacturer=new BatchTraceabilityManufacturer();
        if (env.equals("prd") || env.equals("uat")){
            String bloo = checkToken(batchDto.getTokenIqa());
            if (StringUtils.isEmpty(bloo)){
                return new DacResponse().message("用户信息已失效");
            }
            batchTraceabilityManufacturer.setPsid(bloo);
        }
//        QueryWrapper<BatchTraceabilityManufacturer> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("manufacturer_eqa_code",batchDto.getManufacturerEqaCode());
//        queryWrapper.eq("sku_jde_code",batchDto.getSkuJdeCode());
//        queryWrapper.eq("production_date",batchDto.getProductionDate());

        batchTraceabilityManufacturer.setManufacturerEqacode(batchDto.getManufacturerEqaCode());
        batchTraceabilityManufacturer.setSkuJdecode(batchDto.getSkuJdecode());
        batchTraceabilityManufacturer.setProductionDate(batchDto.getProductionDate());
//        Page<BatchTraceabilityManufacturer> page = new Page<>(batchDto.getPageNum(), batchDto.getPageSize());
//        IPage<BatchTraceabilityManufacturer> batchTraceabilityRmiIPage = manufacturerMapper.selectPage(page, queryWrapper);
        int startIndex = (batchDto.getPageNum() - 1) * batchDto.getPageSize();
        List<BatchTraceabilityManufacturer> list = manufacturerMapper.queryPage(startIndex, batchDto.getPageSize(), batchTraceabilityManufacturer);
        long count = manufacturerMapper.queryPageCount(batchTraceabilityManufacturer);
        PageResult pageResult = new PageResult();
        pageResult.setData(list);
        pageResult.setTotal(count);
        pageResult.setMessage("Success");
        return new DacResponse().data(pageResult);
    }

    @Override
    public DacResponse getIntegrateInformation(BatchTraceabilityBaseDto batchDto) {
        log.info("获取整合中心列表入参batchDto=========》,{}", batchDto.toString());
        BatchTraceabilityCc batchTraceabilityCc =new BatchTraceabilityCc();
        if (env.equals("prd") || env.equals("uat")){
            String bloo = checkToken(batchDto.getTokenIqa());
            if (StringUtils.isEmpty(bloo)){
                return new DacResponse().message("用户信息已失效");
            }
            batchTraceabilityCc.setPsid(bloo);
        }
//        QueryWrapper<BatchTraceabilityCc> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("supplier_jde_code",batchDto.getSupplierJdeCode());
//        queryWrapper.eq("sku_jde_code",batchDto.getSkuJdeCode());
//        queryWrapper.eq("production_date",batchDto.getProductionDate());
        batchTraceabilityCc.setSupplierJdecode(batchDto.getSupplierJdecode());
        batchTraceabilityCc.setSkuJdecode(batchDto.getSkuJdecode());
        batchTraceabilityCc.setProductionDate(batchDto.getProductionDate());
        int startIndex = (batchDto.getPageNum() - 1) * batchDto.getPageSize();
        List<BatchTraceabilityCc> list = ccMapper.queryPage(startIndex, batchDto.getPageSize(), batchTraceabilityCc);
        long count = ccMapper.queryPageCount(batchTraceabilityCc);
//        Page<BatchTraceabilityCc> page = new Page<>(batchDto.getPageNum(), batchDto.getPageSize());
//        IPage<BatchTraceabilityCc> batchTraceabilityRmiIPage = ccMapper.selectPage(page, queryWrapper);
        PageResult pageResult = new PageResult();
        pageResult.setData(list);
        pageResult.setTotal(count);
        pageResult.setMessage("Success");
        return new DacResponse().data(pageResult);
    }

    @Override
    public DacResponse getLogisticsCenter(BatchTraceabilityBaseDto batchDto) {
        log.info("获取物流中心列表入参batchDto=========》,{}", batchDto.toString());
        BatchTraceabilityLc batchTraceabilityLc =new BatchTraceabilityLc();
        if (env.equals("prd") || env.equals("uat")){
            String bloo = checkToken(batchDto.getTokenIqa());
            if (StringUtils.isEmpty(bloo)){
                return new DacResponse().message("用户信息已失效");
            }
            batchTraceabilityLc.setPsid(bloo);
        }
//        QueryWrapper<BatchTraceabilityLc> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("supplier_jde_code",batchDto.getSupplierJdeCode());
//        queryWrapper.eq("sku_jde_code",batchDto.getSkuJdeCode());
//        queryWrapper.eq("production_date",batchDto.getProductionDate());
        batchTraceabilityLc.setSupplierJdecode(batchDto.getSupplierJdecode());
        batchTraceabilityLc.setSkuJdecode(batchDto.getSkuJdecode());
        batchTraceabilityLc.setProductionDate(batchDto.getProductionDate());
        int startIndex = (batchDto.getPageNum() - 1) * batchDto.getPageSize();
        List<BatchTraceabilityLc> list = batchTraceabilityLcMapper.queryPage(startIndex, batchDto.getPageSize(), batchTraceabilityLc);
        long count = batchTraceabilityLcMapper.queryPageCount(batchTraceabilityLc);
//        Page<BatchTraceabilityLc> page = new Page<>(batchDto.getPageNum(), batchDto.getPageSize());
//        IPage<BatchTraceabilityLc> batchTraceabilityRmiIPage = batchTraceabilityLcMapper.selectPage(page, queryWrapper);
        PageResult pageResult = new PageResult();
        pageResult.setData(list);
        pageResult.setTotal(count);
        pageResult.setMessage("Success");
        return new DacResponse().data(pageResult);
    }

    @Override
    public DacResponse getRestaurantList(BatchTraceabilityBaseDto batchDto) {
        log.info("获取餐厅信息列表入参batchDto=========》,{}", batchDto.toString());
        BatchTraceabilityStore batchTraceabilityStore =new BatchTraceabilityStore();
        if (env.equals("prd") || env.equals("uat")){
            String bloo = checkToken(batchDto.getTokenIqa());
            if (StringUtils.isEmpty(bloo)){
                return new DacResponse().message("用户信息已失效");
            }
            batchTraceabilityStore.setPsid(bloo);
        }
        batchTraceabilityStore.setSupplierJdecode(batchDto.getSupplierJdecode());
        batchTraceabilityStore.setSkuJdecode(batchDto.getSkuJdecode());
        batchTraceabilityStore.setProductionDate(batchDto.getProductionDate());
        int startIndex = (batchDto.getPageNum() - 1) * batchDto.getPageSize();
        List<BatchTraceabilityStore> list = storeMapper.queryPage(startIndex, batchDto.getPageSize(), batchTraceabilityStore);
        long count = storeMapper.queryPageCount(batchTraceabilityStore);
        PageResult pageResult = new PageResult();
        pageResult.setData(list);
        pageResult.setTotal(count);
        pageResult.setMessage("Success");
        return new DacResponse().data(pageResult);
    }

    @Override
    public DacResponse checkUserInfo(UserDto batchDto) {
        log.info("校验用户信息输入参数token=========》,{}", batchDto.toString());
        UserResp resp =new UserResp();
        if(StringUtils.isNotBlank(batchDto.getToken())){
            //调用SCID校验用户接口
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("token",batchDto.getToken());
            String str = null;
            try {
                str = HttpClientUtils.doPostJson("http://172.25.243.47:9999/dm/iframeToken/getPsidRoleFromIframeToken", jsonObject);
            } catch (Exception e) {
                log.error("调用SCID报错信息==========>>"+e.getMessage());
            }
            log.info("调用SCID返回参数=========》,{}", JSONObject.toJSONString(str));
            Map map1 = JSONObject.parseObject(str, Map.class);
            Map data = JSONObject.parseObject(JSONObject.toJSONString(map1.get("data")), Map.class);
            String psid = data.get("psid").toString();
            //如果返回了用户psid，生成tokeniqa 放入redis 返回前端
            String tokenIqa = UUID.randomUUID().toString();
            resp.setTokenIqa(tokenIqa);
            try {
                QueryWrapper<Config> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("iqakey","redis_time");
                Config config = configMapper.selectOne(queryWrapper);
                if (null != config){
                    redisService.set(tokenIqa,psid,Long.parseLong(config.getIqavalue()));
                }
            }catch (Exception e){
                log.error("调用redisService错误信息error=========》",e.getLocalizedMessage());
            }
        }
        return new DacResponse().data(resp);
    }
    
    private String checkToken(String token){
        String info ="";
        try {
            if (StringUtils.isNotBlank(token)){
                info = redisService.get(token);
            }
        }catch (Exception e){
            log.error("error",e.getLocalizedMessage());
        }
        return info;
    }

    private QueryWrapper<BatchTraceabilityMasterData> getMyApplyDtoQueryWrapper(BatchTraceabilityMasterDto queryRequest) {
        QueryWrapper<BatchTraceabilityMasterData> qw = new QueryWrapper<>();

        if (ObjectUtils.isNotNull(queryRequest.getSkuName())) {
            qw.like("sku_name", queryRequest.getSkuName());
        }
        if (ObjectUtils.isNotNull(queryRequest.getManufacturerRmiName())) {
            qw.like("manufacturer_name", queryRequest.getManufacturerName());
        }
        if (ObjectUtils.isNotNull(queryRequest.getProductionDate())) {
            qw.like("production_date", queryRequest.getProductionDate());
        }
        if (ObjectUtils.isNotNull(queryRequest.getRmiSkuName())) {
            qw.like("rmi_sku_name", queryRequest.getRmiSkuName());
        }
        if (ObjectUtils.isNotNull(queryRequest.getManufacturerRmiName())) {
            qw.like("manufacturer_rmi_name",queryRequest.getManufacturerRmiName());
        }
        if (ObjectUtils.isNotNull(queryRequest.getRmiBatchNo())) {
            qw.eq("rmi_batch_no",queryRequest.getRmiBatchNo());
        }
        if (ObjectUtils.isNotNull(queryRequest.getProductName())) {
            qw.eq("sku_name",queryRequest.getProductName());
        }
        if (ObjectUtils.isNotNull(queryRequest.getRestBrand())) {
            qw.like("rest_brand",queryRequest.getRestBrand());
        }
        return qw;
    }


}
