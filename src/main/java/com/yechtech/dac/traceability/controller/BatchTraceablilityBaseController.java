package com.yechtech.dac.traceability.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yechtech.dac.common.controller.BaseController;
import com.yechtech.dac.common.domain.DacResponse;
import com.yechtech.dac.common.service.RedisService;
import com.yechtech.dac.traceability.dao.ConfigMapper;
import com.yechtech.dac.traceability.domain.*;
import com.yechtech.dac.traceability.dto.*;
import com.yechtech.dac.traceability.service.BatchTraceablilityBaseService;
import com.yechtech.dac.traceability.utils.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description : 批次追溯主档数据Controller
 * @author : Turbozhang
 * @createTime : 2020/11/11 14:26
 * @version :1.0
 */
@RestController
@RequestMapping("/api/rest")
public class BatchTraceablilityBaseController extends BaseController {

    @Resource
    private BatchTraceablilityBaseService batchTraceablilityBaseService;

    @Resource
    ConfigMapper configMapper;

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


}
