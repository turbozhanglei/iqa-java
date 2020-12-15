package com.yechtech.dac.traceability.service;

import com.yechtech.dac.common.domain.DacResponse;
import com.yechtech.dac.traceability.domain.BatchTraceabilityMasterData;
import com.yechtech.dac.traceability.dto.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;

/**
 * @description:
 * @author:Turbozhang
 * @createTime:2020/11/11 14:50
 * @version:1.0
 */
public interface ExcelService {

    HSSFWorkbook getHSSFWorkbook(BatchTraceabilityDto queryRequest,String psid);
}
