package com.fyj.crm.workbench.service;

import com.fyj.crm.workbench.domain.Clue;
import com.fyj.crm.workbench.domain.Tran;

public interface ClueService {
    boolean save(Clue clue);

    Clue getClueById(String id);

    boolean unbund(String id);

    boolean bund(String[] activityIds, String clueId);


    boolean convert(String clueId, Tran t, String createBy);
}
