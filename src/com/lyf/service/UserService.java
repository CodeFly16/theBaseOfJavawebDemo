package com.lyf.service;

import com.lyf.domain.PageBean;
import com.lyf.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 用户管理的业务接口
 */
public interface UserService {

    /**
     * 查询所有用户信息
     * @return
     */
    List<User> findAll();

    void addUser(User user);

    void deleteUser(String id);

    User findById(String id);

    void updateUser(User user);

    void delSelectedUser(String[] ids);

    PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> conditon);
}
