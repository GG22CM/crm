package com.fyj.crm.workbench.dao;

import com.fyj.crm.workbench.domain.Tran;
import com.fyj.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranDao {
    int insert(Tran record);

    int insertSelective(Tran record);

    Tran selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Tran record);

    int updateByPrimaryKey(Tran record);

    Tran detail(String id);


    int updateStage(Tran tran);

    int getTotal();

    List<Map<String, Object>> getCountGroupByStage();
}