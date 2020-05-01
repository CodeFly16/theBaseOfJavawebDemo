package com.lyf.service.impl;

import com.lyf.dao.UserDao;
import com.lyf.dao.impl.UserDaoImpl;
import com.lyf.domain.PageBean;
import com.lyf.service.UserService;
import com.lyf.domain.User;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();

    @Override
    public List<User> findAll() {
        //调用Dao完成查询
        return dao.findAll();
    }

    @Override
    public void addUser(User user) {
        dao.add(user);
    }

    @Override
    public void deleteUser(String id) {
        dao.delete(Integer.parseInt(id));
    }

    @Override
    public User findById(String id) {
        return dao.findById(Integer.parseInt(id));
    }

    @Override
    public void updateUser(User user) {
        dao.update(user);
    }

    @Override
    public void delSelectedUser(String[] ids) {
        for (String id : ids) {
            dao.delete(Integer.parseInt(id));
        }
    }

    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> conditon) {
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);
        //创建空的PageBean
        PageBean<User> pb = new PageBean<User>();
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        //查询总记录数
        int totcalCount = dao.findTotalCount(conditon);
        pb.setTotalCount(totcalCount);
        //查询list集合
        int start = (currentPage - 1) * rows;
        List<User> list = dao.findByPage(start, rows,conditon);
        pb.setList(list);
        //计算总的页码数
        int totalPage = (totcalCount % rows == 0) ? totcalCount / rows : (totcalCount / rows + 1);
        pb.setTotalPage(totalPage);
        return pb;
    }

    public User login(User user) {
        return dao.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
}
