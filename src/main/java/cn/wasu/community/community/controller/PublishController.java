package cn.wasu.community.community.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;

    @GetMapping(value = "/publish")
    public String publish(){
        return "publish";
    }

    @GetMapping(value = "/publish/{id}")
    public String edit(@PathVariable("id")Integer id,Model model){
        //通过Id获取question对象
        QuestionDTO question=questionService.getById(id);
        //数据页面回显
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }

    @PostMapping(value = "/publish")
    public String doPublish(@RequestParam(value = "title")String title,@RequestParam(value = "id")Integer id,
                            @RequestParam(value = "description")String description,
                            @RequestParam(value = "tag")String tag, HttpServletRequest request, Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(ObjectUtils.isEmpty(title)){
            model.addAttribute("error","标题不能为空!");
            return "publish";
        }
        if(ObjectUtils.isEmpty(description)){
            model.addAttribute("error","内容不能为空!");
            return "publish";
        }
        if(ObjectUtils.isEmpty(tag)){
            model.addAttribute("error","标签不能为空!");
            return "publish";
        }
        User user= (User) request.getSession().getAttribute("user");
        if(ObjectUtils.isEmpty(user)){
            model.addAttribute("error","用户未登录!");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
