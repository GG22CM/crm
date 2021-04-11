package com.fyj.crm.workbench.dao;

import com.fyj.crm.workbench.domain.ContactsRemark;

public interface ContactsRemarkDao {
    int insert(ContactsRemark record);

    int insertSelective(ContactsRemark record);

    ContactsRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContactsRemark record);

    int updateByPrimaryKey(ContactsRemark record);
}