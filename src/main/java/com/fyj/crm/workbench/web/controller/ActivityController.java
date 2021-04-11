package com.fyj.crm.workbench.web.controller;

import com.fyj.crm.settings.domain.User;
import com.fyj.crm.settings.service.UserService;
import com.fyj.crm.util.DateTimeUtil;
import com.fyj.crm.util.UUIDUtil;
import com.fyj.crm.vo.PaginationVO;
import com.fyj.crm.workbench.domain.Activity;
import com.fyj.crm.workbench.domain.ActivityRemark;
import com.fyj.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {
    @Resource
    private ActivityService activityService;
    @Resource
    private UserService userService;

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public List<User> getUserList(){
        System.out.println("取得用户信息列表");
        List<User> users=userService.getUserList();
        return  users;
    }

    @RequestMapping("/save.do")
    @ResponseBody
    public Map<String,Boolean> getUserList(Activity activity, HttpServletRequest request){
        System.out.println("执行添加操作");
        //将参数补全
        String id= UUIDUtil.getUUID();
        //创建时间
        String createTime= DateTimeUtil.getSysTime();
        //创建人
        String createBy=((User) request.getSession().getAttribute("user")).getName();
        activity.setId(id);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);


        Map<String,Boolean> map=new HashMap<String,Boolean>();
        boolean flag=true;
        //执行增加方法
        int count=activityService.save(activity);
        if(count!=1){
            flag=false;
        }
        map.put("success",flag);
        return  map;
    }

    @RequestMapping("/pageList.do")
    @ResponseBody
    public PaginationVO<Activity> pageList(Integer pageNo, Integer pageSize, String name, String owner, String startDate, String endDate){
        System.out.println("分页查询");

        //我使用pageHelper所以不需要多余的操作

        Map<String,Object> map=new HashMap<String,Object>();
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);

        //将map传入业务层
        /*
        需要得到市场活动信息列表和查询的总条数
        使用vo封装
        PaginationVo<T>
                int total;
                List<T> dataList;
         */
        PaginationVO<Activity> activityPaginationVO=activityService.pageList(map);
        return activityPaginationVO;

    }


    @RequestMapping("/delete.do")
    @ResponseBody
    public Map<String,Boolean> delete(String[] id){
        System.out.println("删除操作");

        String ids[]=id;


        Boolean flag=activityService.delete(ids);

        //封装成Map
        Map<String,Boolean> map=new HashMap<String,Boolean>();
        map.put("success",flag);
        return  map;

    }


    @RequestMapping("/getUserListAndActivity.do")
    @ResponseBody
    public Map<String,Object> getUserListAndActivity(String id){
        System.out.println("查询用户列表和单条，填充市场活动修改的模态窗口");

        Map<String,Object> map=activityService.getUserListAndActivity(id);
        return  map;

    }


    @RequestMapping("/update.do")
    @ResponseBody
    public Map<String,Boolean> update(Activity activity,HttpServletRequest request){
        System.out.println("执行修改操作");

        //修改时间
        String editTime= DateTimeUtil.getSysTime();
        //修改人
        String editBy=((User) request.getSession().getAttribute("user")).getName();
        activity.setEditTime(editTime);
        activity.setEditBy(editBy);


        Map<String,Boolean> map=new HashMap<String,Boolean>();
        boolean flag=true;
        //执行增加方法
        int count=activityService.update(activity);
        if(count!=1){
            flag=false;
        }
        map.put("success",flag);
        return  map;
    }


    @RequestMapping("/detail.do")
    public ModelAndView detail(String id){
        System.out.println("跳转到详细信息页");

        ModelAndView mv=new ModelAndView();

        Activity activity=activityService.detail(id);

        mv.addObject("activity",activity);
        mv.setViewName("/workbench/activity/detail.jsp");
        return  mv;
    }


    @RequestMapping("/getRemarkListByActivityId.do")
    @ResponseBody
    public List<ActivityRemark> getRemarkListByActivityId(String id){
        System.out.println("执行取得备注列表操作");
        List<ActivityRemark> activityRemarks=activityService.getRemarkListByActivityId(id);
        return  activityRemarks;
    }


    @RequestMapping("/deleteRemark.do")
    @ResponseBody
    public Map<String,Boolean> deleteRemark(String id){
        System.out.println("执行删除操作");
        Map<String,Boolean> map=new HashMap<String,Boolean>();
        boolean flag=activityService.deleteRemark(id);
        map.put("success",flag);
        return  map;
    }

    @RequestMapping("/saveRemark.do")
    @ResponseBody
    public Map<String,Object> saveRemark(ActivityRemark activityRemark, HttpServletRequest request){
        System.out.println("执行市场活动详细信息保存操作");
        //将参数补全
        String id= UUIDUtil.getUUID();
        //创建时间
        String createTime= DateTimeUtil.getSysTime();
        //创建人
        String createBy=((User) request.getSession().getAttribute("user")).getName();
        activityRemark.setId(id);
        activityRemark.setCreateTime(createTime);
        activityRemark.setCreateBy(createBy);
        activityRemark.setEditFlag("0");


        Map<String,Object> map=new HashMap<String,Object>();
        boolean flag=activityService.saveRemark(activityRemark);
        map.put("success",flag);
        map.put("activityRemark",activityRemark);
        return  map;
    }

    @RequestMapping("/updateRemark.do")
    @ResponseBody
    public Map<String,Object> updateRemark(ActivityRemark activityRemark,HttpServletRequest request){
        System.out.println("执行修改市场活动备注操作");

        //修改时间
        String editTime= DateTimeUtil.getSysTime();
        //修改人
        String editBy=((User) request.getSession().getAttribute("user")).getName();
        activityRemark.setEditTime(editTime);
        activityRemark.setEditBy(editBy);
        activityRemark.setEditFlag("1");


        Map<String,Object> map=new HashMap<String,Object>();
        boolean flag=activityService.updateRemark(activityRemark);
        //执行增加方法
        map.put("success",flag);
        map.put("activityRemark",activityRemark);
        return  map;
    }
}
