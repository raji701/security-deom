package com.imaginnovate.websecuritycheck.controller;

import com.imaginnovate.websecuritycheck.model.AppUser;
import com.imaginnovate.websecuritycheck.service.AppUserServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    private final AppUserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(AppUserServiceImpl userService ,PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public List<AppUser> getAll()
    {
        return userService.allUsers();
    }

    @PostMapping("/new-user")
    public AppUser saveUser(@RequestBody AppUser appUser)
    {
        Integer id = appUser.getUserId();
        String email = appUser.getEmail();
        String password = appUser.getPassword();
        String newpassword = passwordEncoder.encode(password);
        String username = appUser.getUsername();
        Set<String> authorities = appUser.getRoles();

        AppUser nappUser = new AppUser(id,username,newpassword,email,authorities);
        return userService.addUser(nappUser);
    }
}
