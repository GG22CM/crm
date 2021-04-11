package com.fyj.crm.workbench.dao;

import com.fyj.crm.workbench.domain.Contacts;

public interface ContactsDao {
    int insert(Contacts record);

    int insertSelective(Contacts record);

    Contacts selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Contacts record);

    int updateByPrimaryKey(Contacts record);
}