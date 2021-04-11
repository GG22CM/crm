package com.fyj.crm.web.listener;

import com.fyj.crm.settings.domain.DicValue;
import com.fyj.crm.settings.service.DicService;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class SysInitListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        /*
            该方法是监听上下文域对象的方法
            对象创建完毕后，马上执行该方法


         */
        ServletContext application=servletContextEvent.getServletContext();
        //取得spring容器，用来获取dicService的bean，注解方式不起作用，可能由于在监听器中的缘故
        ApplicationContext ctx=(ApplicationContext)application.getAttribute("org.springframework.web.context.WebApplicationContext.ROOT");
        DicService dicService= (DicService) ctx.getBean("dicService");

        //取得数据字典
        Map<String, List<DicValue>> map=dicService.getAll();
        Set<String> keySet=map.keySet();

        //装入全局作用域
        for (String  key:keySet) {
            application.setAttribute(key,map.get(key));
        }
//----------------------------------------------------------------------------------------------------------
        //数据字典处理完毕后，处理Stage2Possibility.properties文件
        /*
        处理成map保存到全局作用域
         */
        Map<String,String> pMap=new HashMap<String,String>();

        ResourceBundle rb=ResourceBundle.getBundle("Stage2Possibility");

        Enumeration<String> e= rb.getKeys();

        while(e.hasMoreElements()){
            String key=e.nextElement();
            String value=rb.getString(key);
            pMap.put(key,value);
        }

        //将pMap保存入服务器缓存
        application.setAttribute("pMap",pMap);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
