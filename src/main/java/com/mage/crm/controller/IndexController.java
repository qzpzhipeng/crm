package com.mage.crm.controller;

import com.mage.base.BaseController;
import com.mage.crm.service.UserService;
import com.mage.crm.utils.LoginUserUtil;
import com.mage.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * @param
 * @author qzp
 * @create 2020-03-02 11:47
 */
@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;
    /*
    * 登录页面
    * */
    @RequestMapping("index")
    public String index(){
        return "index";
    }

    /*
    * 后端管理页面
    * */
    @RequestMapping("main")
    public String main(HttpServletRequest request){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        User user = userService.selectByPrimaryKey(userId);
        request.setAttribute("user",user);
        return "main";
    }
}
