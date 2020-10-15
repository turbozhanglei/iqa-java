package com.yechtech.dac.report.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yechtech.dac.common.config.FeignConfig;
import com.yechtech.dac.report.domain.Report;
import com.yechtech.dac.report.domain.SysType;
import com.yechtech.dac.report.dto.SysTypeDto;
import com.yechtech.dac.report.service.SysTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysTypeServiceImpl implements SysTypeService {
    @Autowired
    private FeignConfig feignConfig;

    @Override
    public IPage<Report> getRSysType(SysTypeDto sysTypeDto) {
        log.info("获取系统报表分类： 请求参数=============>>>>{}", JSONObject.toJSONString(sysTypeDto));
        Page<Report> page = new Page<>(sysTypeDto.getPageNum(), sysTypeDto.getPageSize());
        List<SysType> list = new ArrayList<>();
        //typeId 0一级报表类目
        if (sysTypeDto.getTypeId() == 0) {
            JSONArray sysTypeTree = getSysTypeTree(sysTypeDto);
            List<SysType> list1 = new ArrayList<>();
            if (null != sysTypeTree) {
                sysTypeTree.forEach(item -> {
                    if (null != item) {
                        Map map = JSONObject.parseObject(JSONObject.toJSONString(item), Map.class);
                        SysType sysType = new SysType();
                        sysType.setTypeId(Integer.valueOf(map.get("id").toString()));
                        sysType.setTypeName(map.get("label").toString());
                        list1.add(sysType);
                    }
                });
                list.addAll(list1);
            }
        } else {
            //二 三级报表类目
            List<SysType> sysTypeList = getSysTypeList(sysTypeDto);
            list = sysTypeList.stream().filter(o -> o.getPTypeId().equals(sysTypeDto.getTypeId()))
                    .collect(Collectors.toList());
        }

        //分页返回
        if (!CollectionUtils.isEmpty(list)) {
            Long start = (page.getCurrent() - 1) * page.getSize();
            Long end = (page.getCurrent() - 1) * page.getSize() + page.getSize();
            if (start.intValue() < list.size()) {
                List pageList = list.subList(start.intValue(), end.intValue() > list.size() ? list.size() : end.intValue());
                page.setRecords(pageList);
                page.setTotal(list.size());
            }
        }
        return page;
    }


    //获取分类树
    private JSONArray getSysTypeTree(SysTypeDto sysTypeDto) {
        Object tree = feignConfig.getDapFegin(sysTypeDto.getAccessToken()).getTree();
        if (ObjectUtils.isNotEmpty(tree)) {
            JSONObject json = (JSONObject) JSONObject.toJSON(tree);
            if (json.get("code").toString().equals("200")) {
                Object data = JSONObject.parseObject(json.toJSONString()).get("data");
                JSONArray jsonArray = JSONObject.parseObject(data.toString()).getJSONArray("children");
                return jsonArray;
            }
        }
        return null;
    }


    //获取分类树平级数据结构(除去一级结构)
    private List<SysType> getSysTypeList(SysTypeDto sysTypeDto) {
        List<SysType> list = new ArrayList<>();
        Object tree = feignConfig.getDapFegin(sysTypeDto.getAccessToken()).getTree();
        if (ObjectUtils.isNotEmpty(tree)) {
            JSONObject json = (JSONObject) JSONObject.toJSON(tree);
            if (json.get("code").toString().equals("200")) {
                Object data = JSONObject.parseObject(json.toJSONString()).get("data");
                JSONArray jsonArray = JSONObject.parseObject(data.toString()).getJSONArray("children");
                jsonArray.forEach(item -> {
                    Map map = JSONObject.parseObject(JSONObject.toJSONString(item), Map.class);
                    if (ObjectUtils.isNotEmpty(map.get("children"))) {
                        JSONArray jsonArray1 = JSONObject.parseArray(map.get("children").toString());
                        if (null != jsonArray1) {
                            jsonArray1.forEach(item1 -> {
                                Map map1 = JSONObject.parseObject(JSONObject.toJSONString(item1), Map.class);
                                SysType sysType = new SysType();
                                sysType.setTypeId(Integer.valueOf(map1.get("id").toString()));
                                sysType.setPTypeId(Integer.valueOf(map1.get("parentId").toString()));
                                sysType.setTypeName(map1.get("label").toString());
                                list.add(sysType);
                                if (null != map1.get("children")) {
                                    JSONArray jsonArray2 = JSONObject.parseArray(map1.get("children").toString());
                                    if (null != jsonArray2) {
                                        jsonArray2.forEach(item2 -> {
                                            Map map2 = JSONObject.parseObject(JSONObject.toJSONString(item2), Map.class);
                                            SysType sysType1 = new SysType();
                                            sysType1.setTypeId(Integer.valueOf(map2.get("id").toString()));
                                            sysType1.setPTypeId(Integer.valueOf(map2.get("parentId").toString()));
                                            sysType1.setTypeName(map2.get("label").toString());
                                            list.add(sysType1);
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
        return list;
    }

    ;
}
