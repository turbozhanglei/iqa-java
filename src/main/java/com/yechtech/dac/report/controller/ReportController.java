package com.yechtech.dac.report.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yechtech.dac.common.controller.BaseController;
import com.yechtech.dac.common.domain.DacResponse;
import com.yechtech.dac.common.utils.CheckSignatureUtil;
import com.yechtech.dac.report.domain.Report;
import com.yechtech.dac.report.dto.ReportDto;
import com.yechtech.dac.report.dto.ReportVirstDto;
import com.yechtech.dac.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/report")
public class ReportController extends BaseController {

    @Autowired
    private ReportService reportService;

    /**
     * 根据分类获取报表信息(getReport)
     *
     * @return
     */
    @GetMapping("/getReport")
    public DacResponse getReport(ReportDto reportDto) throws UnsupportedEncodingException {
        //验签失败，参数有问题
        if (!CheckSignatureUtil.check(reportDto.getCode(), reportDto.getTimestamp(), reportDto.getSignature())) {
            return new DacResponse().message("验签失败，参数异常");
        }
        IPage<Report> reportIPage = reportService.selectPage(reportDto);
        return new DacResponse().data(getDataTable(reportIPage));
    }


    /**
     * 根据报表id 获取报表信息
     *
     * @return
     */
    @GetMapping("/visitReport")
    public DacResponse visitReport(ReportVirstDto reportVirstDto) throws UnsupportedEncodingException {
        //验签失败，参数有问题
        if (!CheckSignatureUtil.check(reportVirstDto.getCode(), reportVirstDto.getTimestamp(), reportVirstDto.getSignature())) {
            return new DacResponse().message("验签失败，参数异常");
        }
        Report report = reportService.selectOne(reportVirstDto);
        return new DacResponse().data(report);
    }


}
