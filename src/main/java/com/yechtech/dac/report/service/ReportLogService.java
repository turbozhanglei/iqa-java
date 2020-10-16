package com.yechtech.dac.report.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yechtech.dac.report.domain.ReportLog;

/**
 * @author dou
 */
public interface ReportLogService extends IService<ReportLog> {

    void createLog(ReportLog viewLog);

}
