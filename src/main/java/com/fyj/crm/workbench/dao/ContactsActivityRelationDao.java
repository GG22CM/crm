package com.fyj.crm.workbench.dao;

import com.fyj.crm.workbench.domain.ContactsActivityRelation;

public interface ContactsActivityRelationDao {
    int insert(ContactsActivityRelation record);

    int insertSelective(ContactsActivityRelation record);

    ContactsActivityRelation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContactsActivityRelation record);

    int updateByPrimaryKey(ContactsActivityRelation record);
}