package com.yechtech.dac.report.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yechtech.dac.common.config.FeignConfig;
import com.yechtech.dac.report.domain.Report;
import com.yechtech.dac.report.dto.ReportDto;
import com.yechtech.dac.report.dto.ReportVirstDto;
import com.yechtech.dac.report.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    private FeignConfig feignConfig;

    @Override
    public IPage<Report> selectPage(ReportDto reportDto) {
        log.info("根据系统报表分类获取报表信息： 请求参数=============>>>>{}", JSONObject.toJSONString(reportDto));
        Page<Report> page = new Page<>();
        //optType 0代表系统分类
        if(reportDto.getOptType()==0){
            //调用dap服务，获取数据
            Object connectReport = feignConfig.getDapFegin(reportDto.getAccessToken()).getReport(reportDto.getPageSize(), reportDto.getPageNum(),
                    null, reportDto.getTypeId(), Boolean.TRUE);

            //解析返回数据
            List<Report> reportList = new ArrayList();
            Long totalCount = 0L;
            if (!ObjectUtils.isEmpty(connectReport)) {
                JSONObject json = (JSONObject) JSONObject.toJSON(connectReport);
                if (null != json && json.get("code").toString().equals("200")) {
                    Object data = JSONObject.parseObject(json.toJSONString()).get("data");
                    JSONArray jsonArray = JSONObject.parseObject(data.toString()).getJSONArray("result");
                    totalCount = Long.valueOf(JSONObject.parseObject(data.toString()).get("totalCount").toString());
                    if (!CollectionUtils.isEmpty(jsonArray)) {
                        jsonArray.forEach(item -> {
                            Map map = JSONObject.parseObject(JSONObject.toJSONString(item), Map.class);
                            Report report = new Report();
                            report.setReportName((String) map.get("reportName"));
                            report.setReportAccessUrl((String) map.get("visitUrl"));
                            report.setReportImg((String) map.get("filepath"));
                            report.setLastUpdateTime((String) map.get("modifyTime"));
                            report.setTag((String[]) map.get("tag"));
                            if (!(Boolean) map.get("isCollect")) {
                                report.setIsCollect(0);
                            } else {
                                report.setIsCollect(1);
                            }
                            reportList.add(report);
                        });
                    }
                }
            }
            //分页返回
            page.setRecords(reportList);
            page.setTotal(totalCount);
        }

        return page;
    }


    @Override
    public Report selectOne(ReportVirstDto reportVirstDto) {
        log.info("开始进入报表访问： 请求参数=============>>>>{}", JSONObject.toJSONString(reportVirstDto));
        Report report = new Report();
        //判断用户是否有权限读取报表（暂时不做）

        //调用dap服务，获取数据
        Object connectReport = feignConfig.getDapFegin(reportVirstDto.getAccessToken()).getReport(reportVirstDto.getReportId(), Boolean.TRUE);
        if (ObjectUtils.isNotEmpty(connectReport)) {
            JSONObject jsonObject = JSONObject.parseObject(connectReport.toString());
            if (jsonObject.get("code").toString().equals("200")) {
                Map map = JSONObject.parseObject(jsonObject.get("data").toString(), Map.class);

            }
        }
        return null;
    }
}
