package com.fyj.crm.workbench.service.impl;

import com.fyj.crm.util.DateTimeUtil;
import com.fyj.crm.util.UUIDUtil;
import com.fyj.crm.workbench.dao.*;
import com.fyj.crm.workbench.domain.*;
import com.fyj.crm.workbench.service.ClueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {

    //线索相关
    @Resource
    private ClueDao clueDao;
    @Resource
    private ClueActivityRelationDao clueActivityRelationDao;
    @Resource
    private ClueRemarkDao clueRemarkDao;

    //客户相关
    @Resource
    private CustomerDao customerDao;
    @Resource
    private CustomerRemarkDao customerRemarkDao;

    //联系人相关
    @Resource
    private ContactsDao contactsDao;
    @Resource
    private ContactsRemarkDao contactsRemarkDao;
    @Resource
    private ContactsActivityRelationDao contactsActivityRelationDao;

    //交易相关
    @Resource
    private TranDao tranDao;
    @Resource
    private TranHistoryDao tranHistoryDao;


    @Override
    public boolean save(Clue clue) {
        boolean flag=true;
        int count= clueDao.insert(clue);
        if(count!=1){
            flag=false;
        }

        return  flag;
    }

    @Override
    public Clue getClueById(String id) {
        return clueDao.selectById(id);
    }

    @Override
    public boolean unbund(String id) {
        boolean flag=true;
        int count=clueActivityRelationDao.deleteById(id);
        if(count!=1){
            flag=false;
        }
        return  flag;
    }

    @Override
    public boolean bund(String[] activityIds, String clueId) {
        boolean flag=true;
        int count=0;
        ClueActivityRelation clueActivityRelation=new ClueActivityRelation();
        for (String activityId:activityIds
             ) {
            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setActivityid(activityId);
            clueActivityRelation.setClueid(clueId);
            count=clueActivityRelationDao.insert(clueActivityRelation);
            if(count!=1){
                flag=false;
            }

        }
        return flag;
    }

    @Override
    public boolean convert(String clueId, Tran t, String createBy) {
        String createTime= DateTimeUtil.getSysTime();

        boolean flag=true;

        //1)通过线索id获取线索对象，线索对象中封装了线索的详细信息
        Clue clue=clueDao.selectByPrimaryKey(clueId);

        //(2) 通过线索对象提取客户信息，当该客户不存在的时候，新建客户（根据公司的名称精确匹配，判断该客户是否存在！）
        String company=clue.getCompany();

        Customer customer=customerDao.getCustomerByName(company);
        //判断
        if(customer==null){
            customer=new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setOwner(clue.getOwner());
            customer.setNextcontacttime(clue.getNextcontacttime());
            customer.setName(company);
            customer.setDescription(clue.getDescription());
            customer.setCreatetime(createTime);
            customer.setCreateby(createBy);
            customer.setContactsummary(clue.getContactsummary());
            customer.setAddress(clue.getAddress());
            //添加客户
            int count=customerDao.insertSelective(customer);
            if(count!=1){
                flag=false;
            }
        }

        //(3) 通过线索对象提取联系人信息，保存联系人
        Contacts contacts=new Contacts();
        contacts.setSource(clue.getSource());
        contacts.setOwner(clue.getOwner());
        contacts.setNextcontacttime(clue.getNextcontacttime());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setFullname(clue.getFullname());
        contacts.setId(UUIDUtil.getUUID());
        contacts.setEmail(clue.getEmail());
        contacts.setDescription(clue.getDescription());
        contacts.setCustomerid(customer.getId());
        contacts.setCreatetime(createTime);
        contacts.setCreateby(createBy);
        contacts.setContactsummary(clue.getContactsummary());
        contacts.setAppellation(clue.getAppellation());
        contacts.setAddress(clue.getAddress());

        int cont2=contactsDao.insertSelective(contacts);
        if(cont2!=1){
            flag=false;
        }

        //(4) 线索备注转换到客户备注以及联系人备注
        //查询出该条线索下的所有备注
        List<ClueRemark> clueRemarkList=clueRemarkDao.getByClueId(clueId);
        for (ClueRemark clueRemark:clueRemarkList
             ) {
            //添加客户备注
            CustomerRemark customerRemark=new CustomerRemark();
            customerRemark.setNotecontent(clueRemark.getNotecontent());
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setEditflag("0");
            customerRemark.setCreatetime(createTime);
            customerRemark.setCustomerid(customer.getId());
            //添加备注
            int count3=customerRemarkDao.insertSelective(customerRemark);
            if(count3!=1){
                flag=false;
            }
            //添加联系人备注
            ContactsRemark contactsRemark=new ContactsRemark();
            contactsRemark.setContactsid(contacts.getId());
            contactsRemark.setNotecontent(clueRemark.getNotecontent());
            contactsRemark.setEditflag("0");
            contactsRemark.setCreatetime(createTime);
            contactsRemark.setCreateby(createBy);
            contactsRemark.setId(UUIDUtil.getUUID());
            //添加该备注
            int count4=contactsRemarkDao.insertSelective(contactsRemark);
            if(count4!=1){
                flag=false;
            }

        }
        //	(5) “线索和市场活动”的关系转换到“联系人和市场活动”的关系
        //从通过clueId从关联关系表中查询出来相应的clueActivityRelationList
        List<ClueActivityRelation> clueActivityRelationList=clueActivityRelationDao.getListByClueId(clueId);
        for (ClueActivityRelation clueActivityRelation:clueActivityRelationList
        ) {
            ContactsActivityRelation contactsActivityRelation=new ContactsActivityRelation();
            contactsActivityRelation.setActivityid(clueActivityRelation.getActivityid());
            contactsActivityRelation.setContactsid(contacts.getId());
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            //添加到联系人和市场活动关联关系表
            int count5=contactsActivityRelationDao.insert(contactsActivityRelation);
            if(count5!=1){
                flag=false;
            }
        }

        //(6) 如果有创建交易需求，创建一条交易
        if(t!=null){
            //需要创建交易
            t.setSource(clue.getSource());
            t.setOwner(clue.getOwner());
            t.setNextcontacttime(clue.getNextcontacttime());
            t.setDescription(clue.getDescription());
            t.setCustomerid(customer.getId());
            t.setContactsid(contacts.getId());
            t.setContactsummary(clue.getContactsummary());
            int count6=tranDao.insertSelective(t);
            if(count6!=1){
                flag=false;
            }

            //(7) 如果创建了交易，则创建一条该交易下的交易历史
            TranHistory tranHistory=new TranHistory();
            tranHistory.setCreateby(createBy);
            tranHistory.setCreatetime(createTime);
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setTranid(t.getId());
            tranHistory.setExpecteddate(t.getExpecteddate());
            tranHistory.setMoney(t.getMoney());
            tranHistory.setStage(t.getStage());
            int count7=tranHistoryDao.insertSelective(tranHistory);
            if(count7!=1){
                flag=false;
            }

        }

        //(8) 删除线索备注
        for (ClueRemark clueRemark:clueRemarkList
             ) {
            int count8=clueRemarkDao.deleteById(clueRemark.getId());

            if(count8!=1){
                flag=false;
            }
        }

        //(9) 删除线索和市场活动的关系
        for (ClueActivityRelation clueActivityRelation:clueActivityRelationList
             ) {
            int count9=clueActivityRelationDao.deleteById(clueActivityRelation.getId());

            if(count9!=1){
                flag=false;
            }
        }

        //(10) 删除线索
        int count10=clueDao.deleteById(clueId);

        if(count10!=1){
            flag=false;
        }
        return flag;
    }


}
