package com.shop.shop.application;

import com.shop.shop.models.User;
import com.shop.shop.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetUserListService {
    private final UserRepository userRepository;

    public GetUserListService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getUserList() {
        return userRepository.findAllByOrderByIdDesc();
    }
}
