package cn.wasu.community.community.controller;

import cn.wasu.community.community.dto.AccessTokenDTO;
import cn.wasu.community.community.dto.GithubUser;
import cn.wasu.community.community.mapper.UserMapper;
import cn.wasu.community.community.model.User;
import cn.wasu.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AuthController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String secret;

    @Value("${github.client.redirectUri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping(value = "callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken= githubProvider.getAccesstoken(accessTokenDTO);
        GithubUser githubUser=githubProvider.githubUser(accessToken);
        System.out.printf(githubUser.getName());
        //根据是否获取到User判断是否登录成功
        if(!ObjectUtils.isEmpty(githubUser)){
            //登录成功,显示name
            User user = new User();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setCreateTime(System.currentTimeMillis());
            user.setModifyTime(user.getCreateTime());
            userMapper.insert(user);
            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else {
            //登录失败，返回首页，不显示name
            return "redirect:/";
        }
    }
}
