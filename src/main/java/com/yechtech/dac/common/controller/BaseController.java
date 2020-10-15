package com.yechtech.dac.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ChenYu
 * @create 2020-09-10
 * @tag I love java better than girl
 */
public class BaseController {
    @Autowired
    protected ObjectMapper mapper;

    protected Map<String, Object> getDataTable(IPage<?> pageInfo) {
        Map<String, Object> rspData = new HashMap<>();
        rspData.put("result", pageInfo.getRecords());
        rspData.put("totalCount", pageInfo.getTotal());
        return rspData;
    }

    protected Map<String, Object> getDataTable(List<?> list) {
        Map<String, Object> rspData = new HashMap<>();
        rspData.put("result", list);
        rspData.put("totalCount", list.size());
        return rspData;
    }

}
