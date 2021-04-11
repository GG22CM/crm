package com.fyj.crm.settings.service.impl;

import com.fyj.crm.settings.dao.DicTypeDao;
import com.fyj.crm.settings.dao.DicValueDao;
import com.fyj.crm.settings.domain.DicType;
import com.fyj.crm.settings.domain.DicValue;
import com.fyj.crm.settings.service.DicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class DicServiceImpl implements DicService {
    @Resource
    private DicTypeDao dicTypeDao;
    @Resource
    private DicValueDao dicValueDao;


    @Override
    public Map<String, List<DicValue>> getAll() {
        //将字典类型列表取出
        List<DicType> dicTypes=dicTypeDao.getDicTypes();
        //根据字典类型得到相应的dicValue并且封装到Map
        Map<String,List<DicValue>> map=new HashMap<String,List<DicValue>>();
        for (DicType dt:dicTypes
             ) {
            List<DicValue> dicValues=dicValueDao.getDicValuesByTypeCode(dt.getCode());
            //放入map
            map.put(dt.getCode()+"List",dicValues);
        }

        return  map;
    }
}
