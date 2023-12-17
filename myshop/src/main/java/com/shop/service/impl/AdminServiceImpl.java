package com.shop.service.impl;

import com.shop.mapper.AdminMapper;
import com.shop.pojo.Admin;
import com.shop.pojo.AdminExample;
import com.shop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {
        AdminExample example=new AdminExample();

        //添加条件判断，首先去数据库中查找存不存在a_name为name的用户对象
        example.createCriteria().andANameEqualTo(name);

        //select name from admin where a_name="admin"
        List<Admin> list=adminMapper.selectByExample(example);

        //如果查询到了结果，即有这个用户，再判断密码是否正确
        if(list.size()>0){
            //用户名不会重复，所以直接取第一个
            Admin admin=list.get(0);
            //对照数据库中的密码
            if(pwd.equals(admin.getaPass())){
                return admin;
            }
        }
        return null;
    }
}
