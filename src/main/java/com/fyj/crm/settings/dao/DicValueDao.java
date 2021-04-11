package com.fyj.crm.settings.dao;

import com.fyj.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    int insert(DicValue record);

    int insertSelective(DicValue record);

    DicValue selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DicValue record);

    int updateByPrimaryKey(DicValue record);

    List<DicValue> getDicValuesByTypeCode(String code);
}