package com.yechtech.dac.traceability.service;

import com.yechtech.dac.common.domain.DacResponse;
import com.yechtech.dac.traceability.dto.*;

/**
 * @description:
 * @author:Turbozhang
 * @createTime:2020/11/11 14:50
 * @version:1.0
 */
public interface BatchTraceablilityBaseService {

    DacResponse selectPage(BatchTraceabilityMasterDto batchDto);

    DacResponse selectInputList(FilterConditionDto batchDto);

    DacResponse selectDetail(BatchTraceabilityDto batchDto);

    DacResponse getUpstreamRmi(BatchTraceabilityBaseDto batchDto);

    DacResponse getManufacturerList(BatchTraceabilityBaseDto batchDto);

    DacResponse getIntegrateInformation(BatchTraceabilityBaseDto batchDto);

    DacResponse getLogisticsCenter(BatchTraceabilityBaseDto batchDto);

    DacResponse getRestaurantList(BatchTraceabilityBaseDto batchDto);

    DacResponse checkUserInfo(UserDto batchDto);
}
