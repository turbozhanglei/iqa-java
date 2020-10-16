package com.yechtech.dac.report.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yechtech.dac.common.controller.BaseController;
import com.yechtech.dac.common.domain.DacResponse;
import com.yechtech.dac.common.exception.DapException;
import com.yechtech.dac.common.utils.CheckSignatureUtil;
import com.yechtech.dac.common.utils.HttpClientUtils;
import com.yechtech.dac.report.domain.Report;
import com.yechtech.dac.report.domain.ReportLog;
import com.yechtech.dac.report.dto.ReportDto;
import com.yechtech.dac.report.dto.ReportVirstDto;
import com.yechtech.dac.report.service.ReportLogService;
import com.yechtech.dac.report.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/report")
@Slf4j
public class ReportController extends BaseController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportLogService reportLogService;

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
    public DacResponse visitReport(HttpServletRequest request, ReportVirstDto reportVirstDto, HttpServletResponse response) {
        try {
            //验签失败，参数有问题
            if (!CheckSignatureUtil.check(reportVirstDto.getCode(), reportVirstDto.getTimestamp(), reportVirstDto.getSignature())) {
                throw new DapException("验签失败，参数异常");
            }
            String url = reportService.selectOne(reportVirstDto);
            url="https://www.cnblogs.com/wq-code/p/11178572.html";
            if(StringUtils.isEmpty(url)){
                throw new DapException("获取报表访问路径失败...");
            }
            String body = HttpClientUtils.doGet(url, null);//body为获取的html代码
            Document doc = Jsoup.parse(body);
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println(doc.outerHtml());
            //记录报表访问日志
            ReportLog reportLog=new ReportLog();
            reportLog.setAdAccount("test666");
            reportLog.setPsid("test777");
            reportLog.setReportId(318L);
            reportLog.setReportName("test report");
            reportLogService.createLog(reportLog);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new DacResponse().message(e.getMessage());
        }
        return new DacResponse();
    }
}
