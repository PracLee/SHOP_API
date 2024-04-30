package com.shop.shop.application;

import com.shop.shop.security.AuthUserDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LogoutService {
    private final AuthUserDao authUserDao;

    public LogoutService(AuthUserDao authUserDao) {
        this.authUserDao = authUserDao;
    }

    public void logout(String accessToken) {
        authUserDao.removeAccessToken(accessToken);
    }
}