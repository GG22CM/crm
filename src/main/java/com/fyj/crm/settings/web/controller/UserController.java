package com.fyj.crm.settings.web.controller;

import com.fyj.crm.settings.domain.User;
import com.fyj.crm.settings.service.LoginService;
import com.fyj.crm.util.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/settings/user")

public class UserController {
    @Resource
    private LoginService loginService;

    @RequestMapping("/login.do")
    @ResponseBody
    public Map<String,Object> login(String loginAct, String loginPwd, HttpServletRequest request){
        System.out.println("进入到登陆验证操作");

        //将密码的明文形式转换为md5的密文
        loginPwd= MD5Util.getMD5(loginPwd);

        //接收ip地址
        String ip=request.getRemoteAddr();

        System.out.println(ip);

        //map存返回值
        Map<String,Object> map=new HashMap<String,Object>();


        try{
            User user=loginService.login(loginAct,loginPwd,ip);
            request.getSession().setAttribute("user",user);
            /*
               返回 data{”success“:true}
             */
            map.put("success",true);

        }catch (Exception e){
            e.printStackTrace();
            //表示登陆失败
            /*
                返回data {"success":false,msg:"?"}
             */
            map.put("success",false);
            map.put("msg",e.getMessage());
        }
        return  map;
    }
}
