package com.fyj.crm.workbench.dao;

import com.fyj.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {
    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    Customer getCustomerByName(String company);

    List<String> getCustomerName(String name);
}