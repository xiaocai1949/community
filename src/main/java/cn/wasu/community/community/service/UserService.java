package cn.wasu.community.community.service;

import cn.wasu.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserService {
    void createOrUpdate(User user);
}
