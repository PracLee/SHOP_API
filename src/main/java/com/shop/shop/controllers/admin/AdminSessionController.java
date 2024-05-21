package com.shop.shop.controllers.admin;

import com.shop.shop.application.LoginService;
import com.shop.shop.application.LogoutService;
import com.shop.shop.dtos.LoginRequestDto;
import com.shop.shop.dtos.LoginResultDto;
import com.shop.shop.security.AuthUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/session")
public class AdminSessionController {
    private final LoginService loginService;
    private final LogoutService logoutService;

    public AdminSessionController(LoginService loginService,
                                  LogoutService logoutService) {
        this.loginService = loginService;
        this.logoutService = logoutService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResultDto login(@RequestBody LoginRequestDto loginRequestDto) {
        String accessToken = loginService.loginAdmin(
                loginRequestDto.email(), loginRequestDto.password());

        return new LoginResultDto(accessToken);
    }

    @DeleteMapping
    public String logout(Authentication authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        logoutService.logout(authUser.accessToken());

        return "Logout";
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String loginFailed() {
        return "Bad Request";
    }
}