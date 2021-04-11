package com.fyj.crm.workbench.dao;

import com.fyj.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {
    int insert(ClueRemark record);

    int insertSelective(ClueRemark record);

    ClueRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ClueRemark record);

    int updateByPrimaryKey(ClueRemark record);

    List<ClueRemark> getByClueId(String clueId);

    int deleteById(String id);
}