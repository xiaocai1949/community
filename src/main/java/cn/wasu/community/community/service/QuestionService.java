package cn.wasu.community.community.service;

import cn.wasu.community.community.dto.PaginationDTO;
import cn.wasu.community.community.dto.QuestionDTO;
import cn.wasu.community.community.model.Question;

public interface QuestionService {
    PaginationDTO list(Integer page, Integer size);

    PaginationDTO list(Integer id, Integer page, Integer size);

    QuestionDTO getById(Integer id);

    void createOrUpdate(Question question);
}
