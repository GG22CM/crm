package com.fyj.crm.workbench.dao;

import com.fyj.crm.workbench.domain.TranRemark;

public interface TranRemarkDao {
    int insert(TranRemark record);

    int insertSelective(TranRemark record);

    TranRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TranRemark record);

    int updateByPrimaryKey(TranRemark record);
}