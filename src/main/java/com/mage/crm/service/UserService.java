package com.mage.crm.service;

import com.mage.base.BaseService;
import com.mage.crm.dao.UserMapper;
import com.mage.crm.model.UserModel;
import com.mage.crm.utils.AssertUtil;
import com.mage.crm.utils.Md5Util;
import com.mage.crm.utils.UserIDBase64;
import com.mage.crm.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @param
 * @author qzp
 * @create 2020-03-02 15:27
 */
@Service
public class UserService extends BaseService<User,Integer> {

    @Resource
    private UserMapper userMapper;

    public UserModel login(String userName, String userPwd){
        /*
        * 1、参数校验
        *   用户名不能为空
        *   密码不能为空
        * 2、根据用户名 查询用户记录
        * 3、校验用户的存在性:
        *   不存在 -->登录页面，提示记录不存在 方法结束，
        * 4、用户存在：
        *       校验密码：
        *           密码错误-->密码不正确，方法结束，返回登录页面
        * 5、密码正确
        *         用户登录成功，返回用户相关信息，跳转后台系统的首页
        * */
        checkLoginParams(userName,userPwd);
        User user = userMapper.queryUserByUserName(userName);
        AssertUtil.isTrue(null==user,"用户已注销或不存在");
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(userPwd))),"密码不正确");
        return buildUserModelInfo(user);
    }

    private UserModel buildUserModelInfo(User user) {
        return new UserModel(UserIDBase64.encoderUserID(user.getId()),user.getUserName(),user.getTrueName());
    }

    private void checkLoginParams(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"用户名密码不能为空!");
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserPassword(Integer userId,String oldPassword,String newPassword,String confirmPassword){
        /*
        * 1.参数校验 根据用户列表主键，判断用户是否存在
        *   userId 非空 记录存在
        *   oldPassword 非空 必须和数据库中的密码一致
        *   newPassword 非空 新密码必须和老密码不一致
        *   confirmPassword 非空 确认密码必须和新密码一致
        * 2.设置用户新密码
        *   新密码必须加密看，不能是铭文
        * 3，执行更新
        * */
        checkParams(userId,oldPassword,newPassword,confirmPassword);
        User user = selectByPrimaryKey(userId);
        user.setUserPwd(Md5Util.encode(newPassword));
        Integer integer = updateByPrimaryKeySelective(user);
        AssertUtil.isTrue(integer<1,"密码修改失败！");
    }

    private void checkParams(Integer userId, String oldPassword, String newPassword, String confirmPassword) {
        User user = selectByPrimaryKey(userId);
        AssertUtil.isTrue(null==userId || null == user,"用户未登录或者不存在！");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword),"请输入原始密码！");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword),"请输入新密码！");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword),"请输入确认密码！");
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)),"新密码和确认密码不一致，请重新输入确认密码！");
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(oldPassword))),"原始密码不正确，请重新输入原始密码！");
        AssertUtil.isTrue(oldPassword.equals(newPassword),"新密码不能和原始密码一致，请重新输入新密码！");
    }
}
