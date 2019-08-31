package cn.wasu.community.community.controller;

import cn.wasu.community.community.dto.PaginationDTO;
import cn.wasu.community.community.dto.QuestionDTO;
import cn.wasu.community.community.mapper.QuestionMapper;
import cn.wasu.community.community.mapper.UserMapper;
import cn.wasu.community.community.model.Question;
import cn.wasu.community.community.model.User;
import cn.wasu.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,Model model,
                        @RequestParam(value = "page",defaultValue = "1")Integer page,
                        @RequestParam(value = "size",defaultValue = "5")Integer size) {
        PaginationDTO pagination=questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
