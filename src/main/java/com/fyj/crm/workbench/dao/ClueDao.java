package com.fyj.crm.workbench.dao;

import com.fyj.crm.workbench.domain.Clue;

public interface ClueDao {
    int insert(Clue record);

    int insertSelective(Clue record);

    Clue selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Clue record);

    int updateByPrimaryKey(Clue record);

    Clue selectById(String id);

    int deleteById(String clueId);
}