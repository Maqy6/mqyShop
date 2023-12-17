package com.shop.listener;

import com.shop.pojo.ProductType;
import com.shop.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class ProductTypeListener implements ServletContextListener {
    //注册spring框架时，也是以监听器的方式来注册，实现的也是ServletContextListener这个监听器，无法保证哪个监听器先被创建、执行
    //所以不能使用spring容器的依赖注入@Autowired
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //手动从spring容器中取出ProductTypeServiceImpl对象
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext_*.xml");//dao和service
        ProductTypeService productTypeService=(ProductTypeService)context.getBean("ProductTypeServiceImpl");
        List<ProductType> typeList =productTypeService.getAll();
        servletContextEvent.getServletContext().setAttribute("typeList",typeList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
