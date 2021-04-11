package com.fyj.crm.settings.dao;

import com.fyj.crm.settings.domain.DicType;

import java.util.List;

public interface DicTypeDao {
    int insert(DicType record);

    int insertSelective(DicType record);

    DicType selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(DicType record);

    int updateByPrimaryKey(DicType record);

    List<DicType> getDicTypes();
}