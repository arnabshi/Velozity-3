package com.TaskManagement.TaskManagementServer.Config;

import com.TaskManagement.TaskManagementServer.Model.Users;
import com.TaskManagement.TaskManagementServer.Repository.UserRepository;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users u;
        try{
            u=userRepository.findByEmail(username);
        }
        catch (Exception e){
            throw new UsernameNotFoundException("unable to fetch user due to : " + e.getMessage());
        }
        return new User(u.getEmail(),u.getPassword(),u.getAuthorities());
    }
}
