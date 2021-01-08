package com.yechtech.dac.traceability.service.impl;

import com.alibaba.fastjson.JSONArray;
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
import java.math.BigDecimal;
import java.text.NumberFormat;
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

    @Resource
    OdsIqaManuSkuRelMapper odsIqaManuSkuRelMapper;


    @Override
    public DacResponse
    selectPage(BatchTraceabilityMasterDto batchDto) {
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(2);
        BatchTraceabilityMasterData batchTraceabilityMasterData=new BatchTraceabilityMasterData();
        BeanUtils.copyProperties(batchDto,batchTraceabilityMasterData);
        if (env.equals("prd") || env.equals("uat")){
            String bloo = checkToken(batchDto.getTokenIqa());
            Map map = JSONObject.parseObject(bloo,Map.class);
            if (StringUtils.isNotBlank(bloo)){
                batchTraceabilityMasterData.setPsid(map.get("psid").toString());
                batchTraceabilityMasterData.setRoleCode(map.get("roleCode").toString());
            }else{
                return new DacResponse().message("用户信息已失效");
            }
        }
        int startIndex = (batchDto.getPageNum() - 1) * batchDto.getPageSize();
        List<BatchTraceabilityMasterData> list = masterDataMapper.queryPage(startIndex, batchDto.getPageSize(), batchTraceabilityMasterData);
        long count = masterDataMapper.queryPageCount(batchTraceabilityMasterData);
        list.forEach(data->{
            if (null !=data.getTraceRatio()){
                data.setRetrospective(percent.format(data.getTraceRatio().setScale(4, BigDecimal.ROUND_HALF_UP)));
            }else {
                data.setRetrospective("0%");
            }
        });
        PageResult pageResult = new PageResult();
        pageResult.setData(list);
        pageResult.setTotal(count);
        pageResult.setMessage("Success");
        return new DacResponse().data(pageResult);
    }


    @Override
    public DacResponse selectInputList(FilterConditionDto batchDto){
        log.info("获取批次追溯下拉列表入参batchDto=========》,{}", batchDto.toString());
        String psid="";
        String roleCode="";
        if (env.equals("prd") || env.equals("uat")){
            String bloo = checkToken(batchDto.getTokenIqa());
            log.info("获取批次追溯下拉列表bloo=========》,{}",bloo);
            Map map = JSONObject.parseObject(bloo,Map.class);
            if (StringUtils.isEmpty(bloo)){
                return new DacResponse().message("用户信息已失效");
            }
            if(null != map){
                psid = map.get("psid").toString();
                roleCode = map.get("roleCode").toString();
            }
        }

        List<FilterConditionData> responseList  = new ArrayList<>();
        List<FilterConditionData> linkList  = new ArrayList<>();
        switch (batchDto.getType()){
            case 1:
                //品项名称
                EdwDisku edwDisku =new EdwDisku();
                edwDisku.setSkuName(batchDto.getSkuName());
                edwDisku.setPsid(psid);
                edwDisku.setRoleCode(roleCode);
                List<EdwDisku> resultList = edwDiskuMapper.query(edwDisku);
                resultList.forEach(item->{
                    FilterConditionData filterConditionData = new FilterConditionData();
                    filterConditionData.setLable(item.getSkuCode());
                    filterConditionData.setValue(item.getSkuName());
                    responseList.add(filterConditionData);
                });
                break;
            case 2:
                //联调
                if (StringUtils.isNotBlank(batchDto.getSkuJdeCode())){
                    OdsIqaManuSkuRel odsIqaManuSkuRel=new OdsIqaManuSkuRel();
                    odsIqaManuSkuRel.setSkuJdecode(batchDto.getSkuJdeCode());
                    odsIqaManuSkuRel.setStatus("ENABLE");
                    List<OdsIqaManuSkuRel> relList = odsIqaManuSkuRelMapper.query(odsIqaManuSkuRel);
                    relList.forEach(item->{
                        FilterConditionData filterConditionData = new FilterConditionData();
                        filterConditionData.setLable(item.getId());
                        filterConditionData.setValue(item.getManufacturerCnName());
                        responseList.add(filterConditionData);
                    });
                }else {
                    //生产商名称
                    EdwDimanufacturer edwDimanufacturer =new EdwDimanufacturer();
                    edwDimanufacturer.setManufacturerCnName(batchDto.getManufacturerName());
                    List<EdwDimanufacturer> resultManufacturerList = edwDimanufacturerMapper.query(edwDimanufacturer);
                    resultManufacturerList.forEach(item->{
                        FilterConditionData filterConditionData = new FilterConditionData();
                        filterConditionData.setLable(item.getId().toString());
                        filterConditionData.setValue(item.getManufacturerCnName());
                        responseList.add(filterConditionData);
                    });
                }
                break;
            case 3:
                //原料名称
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
                EdwIqaMasterDataRmi edwIqaMasterDataRmi = new EdwIqaMasterDataRmi();
                edwIqaMasterDataRmi.setDataType("2");
                edwIqaMasterDataRmi.setDataValue(batchDto.getManufacturerRmiName());
                List<EdwIqaMasterDataRmi> resultqrmi = edwIqaMasterDataRmiMapper.query(edwIqaMasterDataRmi);
                resultqrmi.forEach(item->{
                    FilterConditionData filterConditionData = new FilterConditionData();
                    filterConditionData.setLable(item.getId().toString());
                    filterConditionData.setValue(item.getDataValue());
                    responseList.add(filterConditionData);
                });
                break;
            case 5:
                EdwDisku edwDiskuPro =new EdwDisku();
                edwDiskuPro.setSkuName(batchDto.getProductName());
                edwDiskuPro.setPsid(psid);
                edwDiskuPro.setRoleCode(roleCode);
                List<EdwDisku> resultPro = edwDiskuMapper.query(edwDiskuPro);
                resultPro.forEach(item->{
                    FilterConditionData filterConditionData = new FilterConditionData();
                    filterConditionData.setLable(item.getId().toString());
                    filterConditionData.setValue(item.getSkuName());
                    responseList.add(filterConditionData);
                });
                break;
            case 6:
                EdwDistore edwDistore =new EdwDistore();
                edwDistore.setBrandNameCn(batchDto.getBrandNameCn());
                List<EdwDistore> resultBrand = edwDistoreMapper.query(edwDistore);
                resultBrand.forEach(item->{
                    FilterConditionData filterConditionData = new FilterConditionData();
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
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(2);
        BatchTraceabilityDetailedSummary batchTraceabilityDetailedSummary =new BatchTraceabilityDetailedSummary();
        SimpleDateFormat format =new SimpleDateFormat();
        QueryWrapper<DetailedSummary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("supplier_jdecode",batchDto.getSupplierJdecode());
        queryWrapper.eq("sku_jdecode",batchDto.getSkuJdecode());
        queryWrapper.eq("production_date",batchDto.getProductionDate());
        queryWrapper.eq("manufacturer_eqacode",batchDto.getManufacturerEqaCode());

        List<DetailedSummary> detailedSummaries = detailedSummaryMapper.selectList(queryWrapper);

        if (CollectionUtils.isNotEmpty(detailedSummaries)){
            BeanUtils.copyProperties(detailedSummaries.get(0),batchTraceabilityDetailedSummary);
            if (null !=batchTraceabilityDetailedSummary.getTraceRatio()){
                batchTraceabilityDetailedSummary.setRetrospective(percent.format(batchTraceabilityDetailedSummary.getTraceRatio().setScale(4, BigDecimal.ROUND_HALF_UP)));
            }else {
                batchTraceabilityDetailedSummary.setRetrospective("0%");
            }
        }

        //获取详情页状态 -上游原料
        BatchTraceabilityRmi queryRmi = new BatchTraceabilityRmi();
        queryRmi.setManufacturerEqaCode(batchDto.getManufacturerEqaCode());
        queryRmi.setSkuJdecode(batchDto.getSkuJdecode());
        queryRmi.setProductionDate(batchDto.getProductionDate());
        queryRmi.setSupplierJdecode(batchDto.getSupplierJdecode());

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
        manufacturer.setSupplierJdecode(batchDto.getSupplierJdecode());
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
        bilityCc.setManufacturerEqacode(batchDto.getManufacturerEqaCode());
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
        traceabilityLc.setManufacturerEqacode(batchDto.getManufacturerEqaCode());
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
        traceabilityStore.setManufacturerEqacode(batchDto.getManufacturerEqaCode());
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
            Map map = JSONObject.parseObject(bloo,Map.class);
            if (StringUtils.isEmpty(bloo)){
                return new DacResponse().message("用户信息已失效");
            }
            batchTraceabilityRmi.setPsid(map.get("psid").toString());
            batchTraceabilityRmi.setRoleCode(map.get("roleCode").toString());
        }
        batchTraceabilityRmi.setManufacturerEqaCode(batchDto.getManufacturerEqaCode());
        batchTraceabilityRmi.setSkuJdecode(batchDto.getSkuJdecode());
        batchTraceabilityRmi.setProductionDate(batchDto.getProductionDate());
        batchTraceabilityRmi.setSupplierJdecode(batchDto.getSupplierJdecode());

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
            Map map = JSONObject.parseObject(bloo,Map.class);
            if (StringUtils.isEmpty(bloo)){
                return new DacResponse().message("用户信息已失效");
            }
            batchTraceabilityManufacturer.setPsid(map.get("psid").toString());
            batchTraceabilityManufacturer.setRoleCode(map.get("roleCode").toString());
        }

        batchTraceabilityManufacturer.setManufacturerEqacode(batchDto.getManufacturerEqaCode());
        batchTraceabilityManufacturer.setSkuJdecode(batchDto.getSkuJdecode());
        batchTraceabilityManufacturer.setProductionDate(batchDto.getProductionDate());
        batchTraceabilityManufacturer.setSupplierJdecode(batchDto.getSupplierJdecode());
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
            Map map = JSONObject.parseObject(bloo,Map.class);
            if (StringUtils.isEmpty(bloo)){
                return new DacResponse().message("用户信息已失效");
            }
            batchTraceabilityCc.setPsid(map.get("psid").toString());
            batchTraceabilityCc.setRoleCode(map.get("roleCode").toString());
        }
        batchTraceabilityCc.setSupplierJdecode(batchDto.getSupplierJdecode());
        batchTraceabilityCc.setSkuJdecode(batchDto.getSkuJdecode());
        batchTraceabilityCc.setProductionDate(batchDto.getProductionDate());
        batchTraceabilityCc.setManufacturerEqacode(batchDto.getManufacturerEqaCode());
        int startIndex = (batchDto.getPageNum() - 1) * batchDto.getPageSize();
        List<BatchTraceabilityCc> list = ccMapper.queryPage(startIndex, batchDto.getPageSize(), batchTraceabilityCc);
        long count = ccMapper.queryPageCount(batchTraceabilityCc);
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
            Map map = JSONObject.parseObject(bloo,Map.class);
            if (StringUtils.isEmpty(bloo)){
                return new DacResponse().message("用户信息已失效");
            }
            batchTraceabilityLc.setPsid(map.get("psid").toString());
            batchTraceabilityLc.setRoleCode(map.get("roleCode").toString());
        }
        batchTraceabilityLc.setSupplierJdecode(batchDto.getSupplierJdecode());
        batchTraceabilityLc.setSkuJdecode(batchDto.getSkuJdecode());
        batchTraceabilityLc.setProductionDate(batchDto.getProductionDate());
        batchTraceabilityLc.setManufacturerEqacode(batchDto.getManufacturerEqaCode());
        int startIndex = (batchDto.getPageNum() - 1) * batchDto.getPageSize();
        List<BatchTraceabilityLc> list = batchTraceabilityLcMapper.queryPage(startIndex, batchDto.getPageSize(), batchTraceabilityLc);
        long count = batchTraceabilityLcMapper.queryPageCount(batchTraceabilityLc);
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
            Map map = JSONObject.parseObject(bloo,Map.class);
            if (StringUtils.isEmpty(bloo)){
                return new DacResponse().message("用户信息已失效");
            }
            batchTraceabilityStore.setPsid(map.get("psid").toString());
            batchTraceabilityStore.setRoleCode(map.get("roleCode").toString());
        }
        batchTraceabilityStore.setSupplierJdecode(batchDto.getSupplierJdecode());
        batchTraceabilityStore.setSkuJdecode(batchDto.getSkuJdecode());
        batchTraceabilityStore.setProductionDate(batchDto.getProductionDate());
        batchTraceabilityStore.setManufacturerEqacode(batchDto.getManufacturerEqaCode());
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
            JSONArray rolearr = (JSONArray) data.get("role");
            String roleCode="";
            for (int i=0;i<rolearr.size();i++){
                Map role = JSONObject.parseObject(JSONObject.toJSONString(rolearr.get(i)), Map.class);
                if (role.get("roleCode").toString().equals("BATCH_TRACE_ALL_DATA")){
                     roleCode = role.get("roleCode").toString();
                }
            }
            String psid = data.get("psid").toString();
            //将psid 和roleCode放入redis
            JSONObject jsonObj=new JSONObject();
            jsonObj.put("psid",psid);
            jsonObj.put("roleCode",roleCode);
            String jsonStr = JSONObject.toJSONString(jsonObj);
            //如果返回了用户psid，生成tokeniqa 放入redis 返回前端
            String tokenIqa = UUID.randomUUID().toString();
            resp.setTokenIqa(tokenIqa);
            try {
                QueryWrapper<Config> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("iqakey","redis_time");
                Config config = configMapper.selectOne(queryWrapper);
                if (null != config){
                    redisService.set(tokenIqa,jsonStr,Long.parseLong(config.getIqavalue()));
                }
            }catch (Exception e){
                log.error("调用redisService错误信息error=========》",e.getLocalizedMessage());
            }
        }
        return new DacResponse().data(resp);
    }

    @Override
    public List<BatchTraceabilityMasterData> queryList(BatchTraceabilityMasterDto queryRequest) {
        BatchTraceabilityMasterData batchTraceabilityMasterData=new BatchTraceabilityMasterData();
        BeanUtils.copyProperties(queryRequest,batchTraceabilityMasterData);
        return masterDataMapper.query(batchTraceabilityMasterData);
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


}
