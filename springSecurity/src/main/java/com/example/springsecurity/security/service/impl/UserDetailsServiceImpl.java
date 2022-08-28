package com.example.springsecurity.security.service.impl;

import com.example.springsecurity.mapper.UserMapper;
import com.example.springsecurity.pojo.UserManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangzhongjie
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        if (!"admin".equals(username)) {
//            throw new UsernameNotFoundException("未查询到用户不存在！！");
//        }
//
//        String password = "pwd";
//        String encode = passwordEncoder.encode(password);
//        UserDetails userDetails= new User(username, encode, AuthorityUtils.commaSeparatedStringToAuthorityList("admin1,admin2"));
        UserManage userManage = userMapper.queryUser(username);

        if (userManage == null){
            throw new UsernameNotFoundException("为查询到该用户!");
        }

        List<String> roles = userMapper.selectAllRolebyUserId(userManage.getId());
        List<String> permissions = userMapper.selectPermissionsByUserId(userManage.getId());

        StringBuffer sf = new StringBuffer();


        for(String role : roles)
        {
            sf.append("ROLE_" + role +",");
        }

        for(String permission : permissions)
        {
            sf.append(permission + ",");
        }

        String roleAndPermission = sf.toString().substring(0 , sf.toString().length() - 1);


        UserDetails userDetails  = new User(username,userManage.getPassword(),AuthorityUtils.commaSeparatedStringToAuthorityList(roleAndPermission));
        return userDetails;
    }
}
