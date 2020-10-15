package com.yechtech.dac.report.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yechtech.dac.common.controller.BaseController;
import com.yechtech.dac.common.domain.DacResponse;
import com.yechtech.dac.common.utils.CheckSignatureUtil;
import com.yechtech.dac.report.domain.Report;
import com.yechtech.dac.report.dto.SysTypeDto;
import com.yechtech.dac.report.service.SysTypeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/report")
@Api("MDAP报表分类接口")
public class SysTypeController extends BaseController {

    @Autowired
    SysTypeService sysTypeService;

    /**
     * 获取报表系统分类
     *
     * @return
     */
    @GetMapping("/type")
    public DacResponse getRSysType(SysTypeDto sysTypeDto) throws UnsupportedEncodingException {
        //验签失败，参数有问题
        if (!CheckSignatureUtil.check(sysTypeDto.getCode(), sysTypeDto.getTimestamp(), sysTypeDto.getSignature())) {
            return new DacResponse().message("验签失败，参数异常");
        }
        DacResponse dacResponse = new DacResponse();
        IPage<Report> page = sysTypeService.getRSysType(sysTypeDto);
        return dacResponse.data(getDataTable(page));
    }


}
