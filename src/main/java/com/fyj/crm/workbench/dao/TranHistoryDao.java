package com.fyj.crm.workbench.dao;

import com.fyj.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {
    int insert(TranHistory record);

    int insertSelective(TranHistory record);

    TranHistory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TranHistory record);

    int updateByPrimaryKey(TranHistory record);

    List<TranHistory> getHistoryListByTranId(String tranId);
}