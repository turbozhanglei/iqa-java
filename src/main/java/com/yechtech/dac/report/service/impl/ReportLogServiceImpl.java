package com.yechtech.dac.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yechtech.dac.report.dao.ReportLogMapper;
import com.yechtech.dac.report.domain.ReportLog;
import com.yechtech.dac.report.service.ReportLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author dou
 */
@Service
public class ReportLogServiceImpl extends ServiceImpl<ReportLogMapper, ReportLog> implements ReportLogService {

    @Override
    public void createLog(ReportLog viewLog) {
        viewLog.setCreateTime(LocalDateTime.now());
        this.save(viewLog);
    }


}
