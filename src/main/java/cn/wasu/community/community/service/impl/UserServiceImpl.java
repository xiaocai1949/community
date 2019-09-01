package cn.wasu.community.community.service.impl;

import cn.wasu.community.community.mapper.UserMapper;
import cn.wasu.community.community.model.User;
import cn.wasu.community.community.model.UserExample;
import cn.wasu.community.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        //判断user是否已存在
        if (ObjectUtils.isEmpty(users) && users.size() == 0) {
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            //更新
            User dbUser = users.get(0);
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            User updteUser = new User();
            updteUser.setGmtModified(System.currentTimeMillis());
            updteUser.setAvatarUrl(user.getAvatarUrl());
            updteUser.setName(user.getName());
            updteUser.setToken(user.getToken());
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updteUser, new UserExample());
        }
    }
}
