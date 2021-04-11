package com.fyj.crm.workbench.web.controller;

import com.fyj.crm.settings.domain.User;
import com.fyj.crm.settings.service.UserService;
import com.fyj.crm.util.DateTimeUtil;
import com.fyj.crm.util.UUIDUtil;
import com.fyj.crm.workbench.domain.Activity;
import com.fyj.crm.workbench.domain.Clue;
import com.fyj.crm.workbench.domain.Tran;
import com.fyj.crm.workbench.service.ActivityService;
import com.fyj.crm.workbench.service.ClueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/clue")
public class ClueController {
    @Resource
    private UserService userService;

    @Resource
    private ClueService clueService;

    @Resource
    private ActivityService activityService;

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public List<User> getUserList(){
        return userService.getUserList();
    }

    @RequestMapping("/save.do")
    @ResponseBody
    public Map<String,Boolean> save(Clue clue, HttpServletRequest request){
        //设置id，创建人，创建时间
        clue.setId(UUIDUtil.getUUID());
        clue.setCreatetime(DateTimeUtil.getSysTime());
        User user=(User)request.getSession().getAttribute("user");
        clue.setCreateby(user.getName());

        //执行添加操作
        boolean flag=clueService.save(clue);
        //添加到map中返回
        Map<String,Boolean> map=new HashMap<String,Boolean>();
        map.put("success",flag);
        return map;
    }

    @RequestMapping("/detail.do")
    public ModelAndView detail(String id){
        ModelAndView mv=new ModelAndView();
        Clue clue= clueService.getClueById(id);
        mv.addObject("clue",clue);
        mv.setViewName("/workbench/clue/detail.jsp");
        return  mv;
    }

    @RequestMapping("/getActivityListByClueId.do")
    @ResponseBody
    public List<Activity> getActivityListByClueId(String clueid){

        System.out.println("根据线索id查询关联的市场活动列表");

        List<Activity> activityList=activityService.getActivityListByClueId(clueid);

        return activityList;
    }


    @RequestMapping("/unbund.do")
    @ResponseBody
    public Map<String,Boolean> unbund(String id){

        //删除线索与当前市场活动关联关系
        boolean flag=clueService.unbund(id);
        //添加到map中返回
        Map<String,Boolean> map=new HashMap<String,Boolean>();
        map.put("success",flag);
        return map;
    }

    @RequestMapping("/getActivityListByNameAndNotByClueId.do")
    @ResponseBody
    public List<Activity> getActivityListByNameAndNotByClueId(String clueid,String name){

        System.out.println("根据搜索框模糊查询未关联的市场活动列表");

        List<Activity> activityList=activityService.getActivityListByNameAndNotByClueId(clueid,name);

        return activityList;
    }

    @RequestMapping("/bund.do")
    @ResponseBody
    public Map<String,Boolean> bund(@RequestParam("activityIds[]") String[] activityIds, String clueId){

        //删除线索与当前市场活动关联关系
        boolean flag=clueService.bund(activityIds,clueId);
        //添加到map中返回
        Map<String,Boolean> map=new HashMap<String,Boolean>();
        map.put("success",flag);
        return map;
    }

    @RequestMapping("/getActivityListByName.do")
    @ResponseBody
    public List<Activity> getActivityListByNameAndNotByClueId(String name){

        System.out.println("根据搜索框模糊查询市场活动列表");

        List<Activity> activityList=activityService.getActivityListByName(name);

        return activityList;
    }

    @RequestMapping("/convert.do")
    public String detail(String clueId, Tran tran,String flag,HttpServletRequest request){
        String resultPage="redirect:/error.jsp";
        //创建人
        String createBy=((User) request.getSession().getAttribute("user")).getName();
        Tran t=null;

        if("a".equals(flag)){
            //需要创建交易
            t=tran;
            t.setId(UUIDUtil.getUUID());
            //创建时间
            String createTime= DateTimeUtil.getSysTime();

            t.setCreateby(createBy);
            t.setCreatetime(createTime);
        }

        boolean flag1=clueService.convert(clueId,t,createBy);

        if(flag1){
            resultPage="redirect:/workbench/clue/index.jsp";
        }

        return resultPage;

    }
}
