package com.mage.crm.controller;

import com.mage.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @param
 * @author qzp
 * @create 2020-03-02 11:47
 */
@Controller
public class IndexController extends BaseController {
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
    public String main(){
        return "main";
    }
}
