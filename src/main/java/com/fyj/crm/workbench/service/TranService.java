package com.fyj.crm.workbench.service;


import com.fyj.crm.workbench.domain.Tran;
import com.fyj.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranService {
    boolean save(Tran tran, String customername);

    Tran detail(String id);

    List<TranHistory> getHistoryListByTranId(String tranId);

    boolean changeStage(Tran tran);

    Map<String, Object> getCharts();
}
