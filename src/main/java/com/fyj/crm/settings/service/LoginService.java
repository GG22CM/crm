package com.fyj.crm.settings.service;

import com.fyj.crm.exception.LoginException;
import com.fyj.crm.settings.domain.User;

public interface LoginService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;
}
