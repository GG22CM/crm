package com.fyj.crm.settings.service.impl;

import com.fyj.crm.settings.dao.UserDao;
import com.fyj.crm.settings.domain.User;
import com.fyj.crm.settings.service.UserService;
import com.fyj.crm.workbench.domain.Clue;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Override
    public List<User> getUserList() {

        return userDao.getUserList();
    }

}
