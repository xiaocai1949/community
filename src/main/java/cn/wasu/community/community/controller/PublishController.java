package cn.wasu.community.community.controller;

import cn.wasu.community.community.mapper.QuestionMapper;
import cn.wasu.community.community.mapper.UserMapper;
import cn.wasu.community.community.model.Question;
import cn.wasu.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @GetMapping(value = "/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping(value = "/publish")
    public String doPublish(@RequestParam(value = "title")String title,
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
        Cookie []cookies=request.getCookies();
        User user=null;
        if(!ObjectUtils.isEmpty(cookies)){
            for (Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    String token=cookie.getValue();
                    user=userMapper.findByToken(token);
                    if(user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        if(ObjectUtils.isEmpty(user)){
            model.addAttribute("error","用户未登录!");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        return "redirect:/";
    }
}
