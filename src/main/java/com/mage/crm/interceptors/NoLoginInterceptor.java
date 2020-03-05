package com.mage.crm.interceptors;

import com.mage.crm.exceptions.NoLoginException;
import com.mage.crm.service.UserService;
import com.mage.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @param
 * @author qzp
 * @create 2020-03-05 14:18
 */
public class NoLoginInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
        * 获取cookie 解析用户id
        * 校验用户
        *   如果用户id存在，并且数据库存在对应用户记录，放行，否则进行拦截， 重定向跳转到登录页面index
        * */
        int userIdFromCookie = LoginUserUtil.releaseUserIdFromCookie(request);
        /*出现了一个bug，重定向跳不出，地址栏无法改变*/
        /*if(userIdFromCookie == 0||userService.selectByPrimaryKey(userIdFromCookie)==null){
            response.sendRedirect(request.getContextPath()+"/index");
            return false;
        }*/
        if(userIdFromCookie == 0||userService.selectByPrimaryKey(userIdFromCookie)==null){
            //让全局异常去处理视图跳转
            throw new NoLoginException();
        }
        return true;
    }
}
