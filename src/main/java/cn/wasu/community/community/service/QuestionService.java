package cn.wasu.community.community.service;

import cn.wasu.community.community.dto.PaginationDTO;
import cn.wasu.community.community.dto.QuestionDTO;

public interface QuestionService {
    PaginationDTO list(Integer page, Integer size);

    PaginationDTO list(Integer id, Integer page, Integer size);

    QuestionDTO getById(Integer id);
}
