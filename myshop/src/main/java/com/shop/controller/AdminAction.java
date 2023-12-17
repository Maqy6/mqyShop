package com.shop.controller;

import com.shop.pojo.Admin;
import com.shop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

//保持注解与login.jsp中action地址栏的顺序一致 /admin/login.action
@Controller
@RequestMapping("/admin")
public class AdminAction {
    @Autowired
    AdminService adminService;

    @RequestMapping("/login")
    public String login(String name, String pwd, HttpServletRequest request){

        Admin admin= adminService.login(name,pwd);
        if(admin!=null){
            //登陆成功
            request.setAttribute("admin",admin);
            //根据用户类型，将页面跳转到用户页面或者管理员页面
            if(admin.getaType()==0) return "main";
            else return "user";
        }
        else{
            //登陆失败，则重新返回到登录界面
            request.setAttribute("errmsg","用户名或密码错误！");     //login.jsp中使用el表达式${errmsg}读取错误信息
            return "login";
        }
    }
}
