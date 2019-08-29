package cn.wasu.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublishController {
    @GetMapping(value = "/publish")
    public String publish(){
        return "publish";
    }
}
