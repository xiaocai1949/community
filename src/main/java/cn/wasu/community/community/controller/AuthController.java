package cn.wasu.community.community.controller;

import cn.wasu.community.community.dto.AccessTokenDTO;
import cn.wasu.community.community.dto.GithubUser;
import cn.wasu.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

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
    @GetMapping(value = "callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state){

            AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
            accessTokenDTO.setClient_id(clientId);
            accessTokenDTO.setClient_secret(secret);
            accessTokenDTO.setCode(code);
            accessTokenDTO.setRedirect_uri(redirectUri);
            accessTokenDTO.setState(state);
            String accessToken= githubProvider.getAccesstoken(accessTokenDTO);
            GithubUser githubUser=githubProvider.githubUser(accessToken);
            System.out.printf(githubUser.getName());
            return "index";
    }
}
