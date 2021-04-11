package com.fyj.crm.settings.service.impl;

import com.fyj.crm.exception.LoginException;
import com.fyj.crm.settings.dao.UserDao;
import com.fyj.crm.settings.domain.User;
import com.fyj.crm.settings.service.LoginService;
import com.fyj.crm.util.DateTimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class LoginServiceImpl implements LoginService{

    @Resource
    private UserDao userDao;

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {

        User user=userDao.login(loginAct,loginPwd);

        if(user==null){
            throw new LoginException("账号密码错误");
        }

        //验证失效时间
        String expireTime=user.getExpireTime();
        if(expireTime.compareTo(DateTimeUtil.getSysTime())<0){
            throw  new LoginException("账号已失效");
        }
        //验证锁定状态
        String lockState=user.getLockState();
        if("0".equals(lockState)){
            throw  new LoginException("账号已锁定");
        }
        //验证ip地址
        String allowIps=user.getAllowIps();
        if(allowIps!=null && allowIps !=""){
            if(!allowIps.contains(ip)){
                throw new LoginException("ip地址受限");
            }
        }

        return user;
    }
}
