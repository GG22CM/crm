package com.fyj.workbench.test;

import com.fyj.crm.settings.dao.UserDao;
import com.fyj.crm.settings.domain.User;
import com.fyj.crm.util.UUIDUtil;
import com.fyj.crm.workbench.domain.Activity;
import com.fyj.crm.workbench.service.ActivityService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;


public class ActivityTest {
    @Test
    public void testSelect(){
        ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao= (UserDao) ctx.getBean("userDao");
        List<User> userList=userDao.getUserList();
        for (User u:userList
             ) {
            System.out.println(u);
        }
    }


    //测试断言
    @Test
    public void testSave(){
        ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
        ActivityService activityService= (ActivityService) ctx.getBean("activityServiceImpl");
        Activity activity=new Activity();
        activity.setId(UUIDUtil.getUUID());
        activity.setName("宣传推广会");
        activity.setOwner("40f6cdea0bd34aceb77492a1656d9fb3");
        activity.setCreateBy("");
        activity.setCreateTime("");
        activity.setStartDate("");
        activity.setEndDate("");
        activity.setCost("");
        activity.setDescription("");
        int flag=activityService.save(activity);
        Assert.assertEquals(1,flag);
    }
}
