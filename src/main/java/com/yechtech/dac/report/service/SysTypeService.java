package com.yechtech.dac.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yechtech.dac.report.domain.Report;
import com.yechtech.dac.report.dto.SysTypeDto;


public interface SysTypeService  {

    IPage<Report> getRSysType(SysTypeDto sysTypeDto);
}
