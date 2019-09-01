package cn.wasu.community.community.service;

import cn.wasu.community.community.dto.CommentDTO;
import cn.wasu.community.community.model.Comment;

import java.util.List;

public interface CommentService {
    void insert(Comment comment);

    List<CommentDTO> listQuestionById(Long id);
}
