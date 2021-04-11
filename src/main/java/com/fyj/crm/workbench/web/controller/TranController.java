package com.fyj.crm.workbench.web.controller;

import com.fyj.crm.settings.domain.User;
import com.fyj.crm.settings.service.UserService;
import com.fyj.crm.util.DateTimeUtil;
import com.fyj.crm.util.UUIDUtil;
import com.fyj.crm.workbench.domain.Tran;
import com.fyj.crm.workbench.domain.TranHistory;
import com.fyj.crm.workbench.service.CustomerService;
import com.fyj.crm.workbench.service.TranService;
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
@RequestMapping("/workbench/transaction")
public class TranController {
    @Resource
    private TranService tranService;
    @Resource
    private UserService userService;
    @Resource
    private CustomerService customerService;

    @RequestMapping("/add.do")
    public ModelAndView add(){
        ModelAndView mv=new ModelAndView();
        List<User> userList=userService.getUserList();
        mv.addObject("userList",userList);
        mv.setViewName("/workbench/transaction/save.jsp");
        return mv;
    }

    @RequestMapping("/getCustomerName.do")
    @ResponseBody
    public List<String> getCustomerName(String name){

        List<String> names=customerService.getCustomerName(name);
        return  names;
    }

    @RequestMapping("/save.do")
    public String save(Tran tran, String customername, HttpServletRequest request){
        String resultView="/error.jsp";
        //创建时间
        String createTime= DateTimeUtil.getSysTime();
        //创建人
        String createBy=((User) request.getSession().getAttribute("user")).getName();


        tran.setId(UUIDUtil.getUUID());
        tran.setCreatetime(createTime);
        tran.setCreateby(createBy);

        //执行添加操作
        boolean flag=tranService.save(tran,customername);

        if(flag){
            resultView="redirect:/workbench/transaction/index.jsp";
        }

        return resultView;
    }


    @RequestMapping("/detail.do")
    public ModelAndView detail(String id,HttpServletRequest request){
        ModelAndView mv=new ModelAndView();
        Tran tran=tranService.detail(id);
        //处理可能性
        String stage=tran.getStage();
        Map<String,String> pMap= (Map<String, String>) request.getServletContext().getAttribute("pMap");
        tran.setPossibility(pMap.get(stage));
        mv.addObject("tran",tran);
        mv.setViewName("/workbench/transaction/detail.jsp");
        return mv;
    }


    @RequestMapping("/showHistoryListByTranId.do")
    @ResponseBody
    public List<TranHistory> showHistoryListByTranId(String tranId,HttpServletRequest request){

        List<TranHistory> tranHistoryList=tranService.getHistoryListByTranId(tranId);
        //阶段和可能性之间的对应关系
        Map<String,String> pMap= (Map<String, String>) request.getServletContext().getAttribute("pMap");
        for (TranHistory t:tranHistoryList
             ) {
            t.setPossibility(pMap.get(t.getStage()));
        }
        return  tranHistoryList;
    }

    @RequestMapping("/changeStage.do")
    @ResponseBody
    public Map<String,Object> changeStage(Tran tran,HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        //修改时间
        String editTime= DateTimeUtil.getSysTime();
        //修改人
        String editBy=((User) request.getSession().getAttribute("user")).getName();
        //可能性
        String possibility=((Map<String,String>)request.getServletContext().getAttribute("pMap")).get(tran.getStage());
        tran.setEditby(editBy);
        tran.setEdittime(editTime);
        tran.setPossibility(possibility);

        boolean flag=tranService.changeStage(tran);

        //封装成map
        map.put("success",flag);
        map.put("tran",tran);

        return map;

    }

    @RequestMapping("/getCharts.do")
    @ResponseBody
    public Map<String,Object> getCharts(){
       Map<String,Object> map=tranService.getCharts();

        return map;

    }
}
