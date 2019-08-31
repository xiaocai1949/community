package cn.wasu.community.community.controller;

import cn.wasu.community.community.dto.CommentDTO;
import cn.wasu.community.community.dto.ResultDTO;
import cn.wasu.community.community.exception.CustomizaErrorCode;
import cn.wasu.community.community.mapper.CommentMapper;
import cn.wasu.community.community.model.Comment;
import cn.wasu.community.community.model.User;
import cn.wasu.community.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO, HttpServletRequest request){
        User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorof(CustomizaErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
