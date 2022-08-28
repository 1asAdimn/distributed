package com.example.springsecurity.mapper;


import com.example.springsecurity.pojo.UserManage;

import java.util.List;

/**
 * @author zhangzhongjie
 */
public interface UserMapper {


    public UserManage queryUser(String username);

    public List<String> selectAllRolebyUserId(Integer userId);

    public List<String> selectPermissionsByUserId(Integer userId) ;
}
