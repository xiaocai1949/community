package cn.wasu.community.community.service;

import cn.wasu.community.community.dto.QuestionDTO;
import cn.wasu.community.community.model.Question;

import java.util.List;

public interface QuestionService {
    List<QuestionDTO> list();
}
