package com.mage.crm.dao;

import com.mage.base.BaseMapper;
import com.mage.crm.vo.User;

public interface UserMapper extends BaseMapper<User,Integer> {

    public User queryUserByUserName(String userName);
}