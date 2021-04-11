package com.fyj.crm.workbench.service.impl;

import com.fyj.crm.settings.dao.UserDao;
import com.fyj.crm.settings.domain.User;
import com.fyj.crm.vo.PaginationVO;
import com.fyj.crm.workbench.dao.ActivityDao;
import com.fyj.crm.workbench.dao.ActivityRemarkDao;
import com.fyj.crm.workbench.domain.Activity;
import com.fyj.crm.workbench.domain.ActivityRemark;
import com.fyj.crm.workbench.service.ActivityService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Resource
    private ActivityDao activityDao;
    @Resource
    private ActivityRemarkDao activityRemarkDao;
    @Resource
    private UserDao userDao;


    @Override
    public int save(Activity activity) {
        return activityDao.save(activity);

    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {


        //取得dataList(分页)
        Integer pageNo=(Integer) map.get("pageNo");
        Integer pageSize= (Integer) map.get("pageSize");

        Page page=PageHelper.startPage(pageNo,pageSize);
        List<Activity> activityList=activityDao.getActivityListByCondition(map);

        //取得total
        int total= (int) page.getTotal();

        //将total和dataList封装到vo中
        PaginationVO<Activity> paginationVO=new PaginationVO<Activity>();
        paginationVO.setDataList(activityList);
        paginationVO.setTotal(total);


        //返回
        return paginationVO;
    }

    @Override
    public Boolean delete(String[] ids) {

        boolean flag=true;
         //先查询出需要删除的备注的数量(用作判断)
        int count=activityRemarkDao.getCountByAids(ids);

        //删除备注，返回数量
        int result=activityRemarkDao.deleteByAids(ids);
        if(count!=result){
            flag=false;
        }

        //删除市场活动
        int ar=activityDao.deleteByIds(ids);
        if(ar!=ids.length){
            flag=false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        //取得用户列表
        List<User> userList=userDao.getUserList();

        //取得单条市场活动
        Activity activity=activityDao.getActivityById(id);

        //封装成map
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("uList",userList);
        map.put("a",activity);
        return map;
    }

    @Override
    public int update(Activity activity) {
        return activityDao.update(activity);
    }

    @Override
    public Activity detail(String id) {
        Activity activity=activityDao.getActivityAndOwnerById(id);
        return activity;
    }

    @Override
    public List<ActivityRemark> getRemarkListByActivityId(String id) {

        return activityRemarkDao.getRemarkListByActivityId(id);

    }

    @Override
    public boolean deleteRemark(String id) {
        boolean flag=true;
        int count=activityRemarkDao.deleteById(id);
        if(count!=1){
            flag=false;
        }
        return  flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark activityRemark) {
        boolean flag=true;
        int count=activityRemarkDao.saveRemark(activityRemark);
        if(count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean updateRemark(ActivityRemark activityRemark) {
        boolean flag=true;
        int count=activityRemarkDao.updateRemark(activityRemark);
        if(count!=1){
            flag=false;
        }
        return  flag;
    }

    @Override
    public List<Activity> getActivityListByClueId(String clueid) {
        List<Activity> activityList=activityDao.getActivityListByClueId(clueid);
        return activityList;
    }

    @Override
    public List<Activity> getActivityListByNameAndNotByClueId(String clueid, String name) {


        return activityDao.getActivityListByNameAndNotByClueId(clueid,name);
    }

    @Override
    public List<Activity> getActivityListByName(String name) {
        return activityDao.getActivityListByName(name);
    }
}
