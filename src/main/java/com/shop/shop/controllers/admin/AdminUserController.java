package com.shop.shop.controllers.admin;

import com.shop.shop.annotations.AdminRequired;
import com.shop.shop.application.GetUserListService;
import com.shop.shop.application.GetUserService;
import com.shop.shop.dtos.AdminUserListDto;
import com.shop.shop.dtos.UserDto;
import com.shop.shop.models.User;
import com.shop.shop.models.UserId;
import com.shop.shop.security.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AdminRequired
@RestController
@RequestMapping("/admin/users")
public class AdminUserController {
    private final GetUserListService getUserListService;
    private final GetUserService getUserService;

    public AdminUserController(GetUserListService getUserListService,
                               GetUserService getUserService) {
        this.getUserListService = getUserListService;
        this.getUserService = getUserService;
    }


    @GetMapping("/me")
    public UserDto me(Authentication authentication) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        UserId id = new UserId(authUser.id());
        User user = getUserService.getAdminUser(id);
        return UserDto.of(user);
    }

    @GetMapping
    public AdminUserListDto list() {
        List<User> users = getUserListService.getUserList();
        return AdminUserListDto.of(users);
    }
}