package com.mage.crm.controller;

import com.mage.base.BaseController;
import com.mage.crm.model.ResultInfo;
import com.mage.crm.model.UserModel;
import com.mage.crm.service.UserService;
import com.mage.crm.utils.LoginUserUtil;
import com.mage.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    @RequestMapping("user/login")
    @ResponseBody
    public ResultInfo login(String userName,String userPwd){
        /*ResultInfo resultInfo = new ResultInfo();*/
        UserModel userModel = userService.login(userName, userPwd);
       /* resultInfo.setResult(userModel);//???*/
        /*try {
            Object result = resultInfo.getResult();
        } catch (ParamsException e) {
            e.printStackTrace();
            resultInfo.setMsg(e.getMsg());
            resultInfo.setCode(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setCode(500);
            resultInfo.setMsg("failed");
        }*/
        //调用父类中的方法，返回resultInfo
        return success("用户登录成功",userModel);
    }
    @RequestMapping("user/updatePassword")
    @ResponseBody
    public ResultInfo updatePassword(HttpServletRequest request,String oldPassword, String newPassword, String confirmPassword) {
        userService.updateUserPassword(LoginUserUtil.releaseUserIdFromCookie(request),oldPassword, newPassword, confirmPassword);
        /*try {
        } catch (ParamsException e) {
            e.printStackTrace();
            resultInfo.setMsg(e.getMsg());
            resultInfo.setCode(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setCode(500);
            resultInfo.setMsg("failed");
        }*/
        //调用父类中的方法，返回resultInfo
        return success();
    }
}
