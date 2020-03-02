package com.mage.crm.controller;

import com.mage.base.BaseController;
import com.mage.crm.service.UserService;
import com.mage.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @param
 * @author qzp
 * @create 2020-03-02 15:27
 */
@Controller
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    @GetMapping("user/queryUserByUserId")
    @ResponseBody
    public User queryUserByUserId(Integer userId){
        return userService.selectByPrimaryKey(userId);
    }
}
