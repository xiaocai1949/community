package cn.wasu.community.community.service;

import cn.wasu.community.community.dto.PaginationDTO;
import cn.wasu.community.community.dto.QuestionDTO;
import cn.wasu.community.community.model.Question;

public interface QuestionService {
    PaginationDTO list(Integer page, Integer size);

    PaginationDTO list(Long id, Integer page, Integer size);

    QuestionDTO getById(Long id);

    void createOrUpdate(Question question);

    void incView(Long id);
}
