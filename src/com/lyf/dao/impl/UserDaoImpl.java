package com.lyf.dao.impl;

import com.lyf.dao.UserDao;
import com.lyf.util.JDBCUtils;
import com.lyf.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<User> findAll() {
        //使用JDBC操作数据库...
        //1.定义sql
        String sql = "select * from user";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));

        return users;
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        try {
            String sql = "select * from user where username= ? and password = ?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void add(User user) {
        String sql = "insert into user values(null,null,null,?,?,?,?,?,?)";
        template.update(sql, user.getName(), user.getGender(),
                user.getAge(), user.getAddress(), user.getQq(), user.getEmail());
    }

    @Override
    public void delete(int id) {
        String sql = "delete from user where id = ?";
        template.update(sql, id);
    }

    @Override
    public User findById(int id) {
        String sql = "select * from user where id= ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
    }

    @Override
    public void update(User user) {
        String sql = "update user set name = ?,gender = ?,age = ?, address = ?,qq = ?,email = ? where id = ?";
        template.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(),
                user.getQq(), user.getEmail(), user.getId());
    }

    @Override
    public int findTotalCount(Map<String, String[]> conditon) {
        //定义模板sql
        String sql = "select count(*)  from  user  WHERE  1  =  1 ";
        StringBuilder stringBuilder = new StringBuilder(sql);
        //遍历map
        Set<String> keySet = conditon.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<>();
        for (String k : keySet) {
            if ("currentPage".equals(k) || "rows".equals(k)) {
                continue;
            }
            String value = conditon.get(k)[0];
            if (value != null && !"".equals(value)) {
                stringBuilder.append(" and " + k + " like ? ");
                params.add("%" + value + "%");
            }
        }
        System.out.println(stringBuilder.toString());
        System.out.println(params);
        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> conditon) {
        String sql = "select * from user limit ?,?";
        return template.query(sql, new BeanPropertyRowMapper<User>(User.class), start, rows);
    }


}
