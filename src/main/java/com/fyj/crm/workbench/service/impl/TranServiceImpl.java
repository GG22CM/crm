package com.fyj.crm.workbench.service.impl;


import com.fyj.crm.util.DateTimeUtil;
import com.fyj.crm.util.UUIDUtil;
import com.fyj.crm.workbench.dao.CustomerDao;
import com.fyj.crm.workbench.dao.TranDao;
import com.fyj.crm.workbench.dao.TranHistoryDao;
import com.fyj.crm.workbench.domain.Customer;
import com.fyj.crm.workbench.domain.Tran;
import com.fyj.crm.workbench.domain.TranHistory;
import com.fyj.crm.workbench.service.TranService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TranServiceImpl implements TranService {
    @Resource
    private TranDao tranDao;
    @Resource
    private TranHistoryDao tranHistoryDao;
    @Resource
    private CustomerDao customerDao;

    @Override
    public boolean save(Tran tran, String customername) {
        /*
        交易添加业务
         */

        boolean flag=true;
        //判断客户
        Customer customer=customerDao.getCustomerByName(customername);
        if(customer==null){
            //创建客户
            customer=new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(tran.getOwner());
            customer.setNextcontacttime(tran.getNextcontacttime());
            customer.setCreateby(tran.getCreateby());
            customer.setCreatetime(tran.getCreatetime());
            customer.setContactsummary(tran.getContactsummary());
            customer.setDescription(tran.getDescription());
            customer.setName(customername);
            //添加客户
            int count1=customerDao.insertSelective(customer);
            if(count1!=1){
                flag=false;
            }
        }
        tran.setCustomerid(customer.getId());

        //执行交易的添加
        int count2=tranDao.insertSelective(tran);
        if(count2!=1){
            flag=false;
        }

        //执行交易历史的添加
        TranHistory tranHistory=new TranHistory();
        tranHistory.setCreateby(tran.getCreateby());
        tranHistory.setCreatetime(tran.getCreatetime());
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranid(tran.getId());
        tranHistory.setExpecteddate(tran.getExpecteddate());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());
        int count3=tranHistoryDao.insertSelective(tranHistory);
        if(count3!=1){
            flag=false;
        }

        return flag;
    }

    @Override
    public Tran detail(String id) {
        return tranDao.detail(id);
    }

    @Override
    public List<TranHistory> getHistoryListByTranId(String tranId) {
        return tranHistoryDao.getHistoryListByTranId(tranId);
    }

    @Override
    public boolean changeStage(Tran tran) {
        boolean flag=true;
        //修改交易状态
        int count1=tranDao.updateStage(tran);

        if(count1!=1){
            flag=false;
        }
        //生成一条交易历史记录
        TranHistory tranHistory=new TranHistory();
        tranHistory.setCreateby(tran.getEditby());
        tranHistory.setCreatetime(DateTimeUtil.getSysTime());
        tranHistory.setStage(tran.getStage());
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setTranid(tran.getId());
        tranHistory.setExpecteddate(tran.getExpecteddate());
        //添加交易历史
        int count2=tranHistoryDao.insertSelective(tranHistory);
        if(count2!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getCharts() {
        List<Map<String,Object>> countList=tranDao.getCountGroupByStage();
        int total=tranDao.getTotal();

        //封装成Map
        Map<String,Object> map=new HashMap<>();
        map.put("total",total);
        map.put("dataList",countList);

        return map;
    }
}
