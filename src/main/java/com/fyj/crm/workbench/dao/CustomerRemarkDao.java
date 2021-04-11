package com.fyj.crm.workbench.dao;

import com.fyj.crm.workbench.domain.CustomerRemark;

public interface CustomerRemarkDao {
    int insert(CustomerRemark record);

    int insertSelective(CustomerRemark record);

    CustomerRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomerRemark record);

    int updateByPrimaryKey(CustomerRemark record);
}