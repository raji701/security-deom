package com.imaginnovate.websecuritycheck.service;

import com.imaginnovate.websecuritycheck.model.AppUser;
import com.imaginnovate.websecuritycheck.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AppUserServiceImpl implements UserDetailsService {

    @Autowired
   UserRepo userRepo;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepo.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("username does not exist"));

        String userName = appUser.getUsername();
        String password = appUser.getPassword();
        Set<String> authorities = appUser.getRoles();
        Set<GrantedAuthority> authoritySet = new HashSet<>();
        authoritySet = authorities.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toSet());

       User user = new User(userName,password,authoritySet);
        return user;
    }

    public AppUser addUser(AppUser user){
        return userRepo.save(user);
    }

    public List<AppUser> allUsers()
    {
        return userRepo.findAll();
    }
}
