package cn.wasu.community.community.service.impl;

import cn.wasu.community.community.dto.PaginationDTO;
import cn.wasu.community.community.dto.QuestionDTO;
import cn.wasu.community.community.mapper.QuestionMapper;
import cn.wasu.community.community.mapper.UserMapper;
import cn.wasu.community.community.model.Question;
import cn.wasu.community.community.model.User;
import cn.wasu.community.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public PaginationDTO list(Integer page, Integer size) {
        //数据总数
        PaginationDTO paginationDTO=new PaginationDTO();
        Integer totalPage;
        Integer totalCount=questionMapper.count();
        if(totalCount % size==0){
            totalPage=totalCount/size;
        }else {
            totalPage=totalCount/size+1;
        }
        paginationDTO.setPagination(totalPage,page);
        if(page<1){
            page=1;
        }
        if(page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }


        //size*(page-1)
        Integer offset=size*(page-1);
        List<Question> questions=questionMapper.list(offset,size);
        List<QuestionDTO>questionDTOList=new ArrayList<>();

        for (Question question:questions){
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    @Override
    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        //数据总数
        PaginationDTO paginationDTO=new PaginationDTO();
        Integer totalPage;
        Integer totalCount=questionMapper.countByUserId(userId);
        if(totalCount % size==0){
            totalPage=totalCount/size;
        }else {
            totalPage=totalCount/size+1;
        }
        paginationDTO.setPagination(totalPage,page);
        if(page<1){
            page=1;
        }
        if(page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }


        //size*(page-1)
        Integer offset=size*(page-1);
        List<Question> questions=questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO>questionDTOList=new ArrayList<>();

        for (Question question:questions){
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }
}
