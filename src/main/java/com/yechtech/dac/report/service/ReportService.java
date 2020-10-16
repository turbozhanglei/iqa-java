package com.yechtech.dac.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yechtech.dac.report.domain.Report;
import com.yechtech.dac.report.dto.ReportDto;
import com.yechtech.dac.report.dto.ReportVirstDto;


public interface ReportService {
    IPage<Report> selectPage(ReportDto reportDto);

    String selectOne(ReportVirstDto reportVirstDto);
}
