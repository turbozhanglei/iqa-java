package com.yechtech.dac.traceability.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yechtech.dac.common.controller.BaseController;
import com.yechtech.dac.common.domain.DacResponse;
import com.yechtech.dac.common.domain.QueryRequest;
import com.yechtech.dac.common.service.RedisService;
import com.yechtech.dac.traceability.dao.ConfigMapper;
import com.yechtech.dac.traceability.domain.*;
import com.yechtech.dac.traceability.dto.*;
import com.yechtech.dac.traceability.service.BatchTraceablilityBaseService;
import com.yechtech.dac.traceability.service.ExcelService;
import com.yechtech.dac.traceability.utils.ExcelUtil;
import com.yechtech.dac.traceability.utils.IqaExcelUtil;
import com.yechtech.dac.traceability.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description : 批次追溯主档数据Controller
 * @author : Turbozhang
 * @createTime : 2020/11/11 14:26
 * @version :1.0
 */
@RestController
@RequestMapping("/api/rest")
@Slf4j
public class BatchTraceablilityBaseController extends BaseController {

    @Resource
    private BatchTraceablilityBaseService batchTraceablilityBaseService;

    @Value("${spring.profiles.active}")
    private String env;

    @Autowired
    private RedisService redisService;

    @Resource
    ExcelService excelService;
    /*
     * description : 获取批次追溯列表
     * param :[batchDto]
     * author :Turbozhang
     * createTime :2020/11/11 14:32
     **/
    @GetMapping("/getTraceBackList")
    public DacResponse getTraceBackList(BatchTraceabilityMasterDto batchDto){
        return batchTraceablilityBaseService.selectPage(batchDto);
    }

    /*
     * description : 模糊匹配下拉选
     * param :[batchDto]
     * author :Turbozhang
     * createTime :2020/11/11 14:32
     **/
    @GetMapping("/getInputList")
    public DacResponse getInputList(FilterConditionDto batchDto){
        return batchTraceablilityBaseService.selectInputList(batchDto);
    }

    /*
     * description : 获取批次追溯明细信息
     * param :[batchDto]
     * author :Turbozhang
     * createTime :2020/11/11 14:32
     **/
    @GetMapping("/detail")
    public DacResponse detail(BatchTraceabilityDto batchDto){
        return batchTraceablilityBaseService.selectDetail(batchDto);
    }

    /*
     * description : 获取上游原料信息
     * param :[batchDto]
     * author :Turbozhang
     * createTime :2020/11/11 14:32
     **/
    @GetMapping("/getUpstreamRmi")
    public DacResponse getUpstreamRmi(BatchTraceabilityBaseDto batchDto){
        return batchTraceablilityBaseService.getUpstreamRmi(batchDto);
    }

    /*
     * description : 获取生产商信息列表
     * param :[batchDto]
     * author :Turbozhang
     * createTime :2020/11/11 14:32
     **/
    @GetMapping("/getManufacturerList")
    public DacResponse getManufacturerList(BatchTraceabilityBaseDto batchDto){
        return batchTraceablilityBaseService.getManufacturerList(batchDto);
    }

    /*
     * description : 获取整合中心列表
     * param :[batchDto]
     * author :Turbozhang
     * createTime :2020/11/11 14:32
     **/
    @GetMapping("/getIntegrateInformation")
    public DacResponse getIntegrateInformation(BatchTraceabilityBaseDto batchDto){
        return batchTraceablilityBaseService.getIntegrateInformation(batchDto);
    }

    /*
     * description : 获取物流中心列表
     * param :[batchDto]
     * author :Turbozhang
     * createTime :2020/11/11 14:32
     **/
    @GetMapping("/getLogisticsCenter")
    public DacResponse getLogisticsCenter(BatchTraceabilityBaseDto batchDto){
        return batchTraceablilityBaseService.getLogisticsCenter(batchDto);
    }

    /*
     * description : 获取餐厅信息列表
     * param :[batchDto]
     * author :Turbozhang
     * createTime :2020/11/11 14:32
     **/
    @GetMapping("/getRestaurantList")
    public DacResponse getRestaurantList(BatchTraceabilityBaseDto batchDto){
        return batchTraceablilityBaseService.getRestaurantList(batchDto);
    }

    /*
     * description : 检查用户信息
     * param :[batchDto]
     * author :Turbozhang
     * createTime :2020/11/11 14:32
     **/
    @GetMapping("/checkUserInfo")
    public DacResponse checkUserInfo(UserDto batchDto){
        return batchTraceablilityBaseService.checkUserInfo(batchDto);
    }



    @GetMapping(value = "/exportExcel")
    public DacResponse export(BatchTraceabilityMasterDto queryRequest, HttpServletResponse response)throws Exception{
        List<String> title=new ArrayList<>();
        String fileName = null;
        String sheetName = null;
        if (env.equals("prd") || env.equals("uat")){
            String bloo = checkToken(queryRequest.getTokenIqa());
            Map map = JSONObject.parseObject(bloo,Map.class);
            if (StringUtils.isNotBlank(bloo)){
                queryRequest.setPsid(map.get("psid").toString());
                queryRequest.setRoleCode(map.get("roleCode").toString());
            }else {
                return  new DacResponse().data(fileName).message("用户信息已失效");
            }
        }
        List<BatchTraceabilityMasterData> list = batchTraceablilityBaseService.queryList(queryRequest);
        if(CollectionUtils.isNotEmpty(list)){
            title.add("品项名称");
            title.add("供应商名称");
            title.add("生产商名称");
            title.add("生产日期");
            title.add("生产量");
            title.add("采购单位");
            title.add("供应商发货数量");
            title.add("到货数量");
            title.add("出货数量");
            title.add("在货数量");
            title.add("追溯率");
            title.add("品项JDEcode");
            title.add("供应商JDEcode");
            title.add("生产商EQA编码");
            fileName = "批次追溯主档"+System.currentTimeMillis()+".xls";
            sheetName = "批次追溯主档";
            HSSFWorkbook wb= ExcelUtil.getHSSFWorkbook(sheetName, title, list, null,"reportLog");
            this.setResponseHeader(response, fileName);
            OutputStream os=response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        }
        return new DacResponse().data(fileName);
    }

    @GetMapping(value = "/exportIqaExcel")
    public DacResponse exportIqaExcel(BatchTraceabilityDto queryRequest, HttpServletResponse response)throws Exception{
        String fileName = null;
        String sheetName = null;
        String psid="";
        String roleCode="";
        if (env.equals("prd") || env.equals("uat")){
            String bloo = checkToken(queryRequest.getTokenIqa());
            Map map = JSONObject.parseObject(bloo,Map.class);
            if (StringUtils.isNotBlank(bloo)){
                psid = map.get("psid").toString();
                roleCode = map.get("roleCode").toString();
            }else {
                return new DacResponse().data(fileName).message("用户信息已失效");
            }
        }
        fileName = "批次追溯详情"+System.currentTimeMillis()+".xls";
        sheetName = "批次追溯详情";
        HSSFWorkbook wb= excelService.getHSSFWorkbook(queryRequest,psid,roleCode);
//        HSSFWorkbook wb= IqaExcelUtil.getHSSFWorkbook(sheetName,queryRequest);
        this.setResponseHeader(response, fileName);
        OutputStream os=response.getOutputStream();
        wb.write(os);
        os.flush();
        os.close();
        return new DacResponse().data(fileName);
    }

    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //准备将Excel的输出流通过response输出到页面下载
            //八进制输出流
            response.setContentType("application/octet-stream");

            //这后面可以设置导出Excel的名称，此例中名为student.xls
            response.setHeader("Content-disposition", "attachment;filename="+fileName);

        } catch (Exception ex) {
            log.error("error", ex.getLocalizedMessage());
        }
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
