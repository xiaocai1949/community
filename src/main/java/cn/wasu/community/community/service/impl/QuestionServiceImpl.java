package cn.wasu.community.community.service.impl;

import cn.wasu.community.community.dto.PaginationDTO;
import cn.wasu.community.community.dto.QuestionDTO;
import cn.wasu.community.community.mapper.QuestionMapper;
import cn.wasu.community.community.mapper.UserMapper;
import cn.wasu.community.community.model.Question;
import cn.wasu.community.community.model.QuestionExample;
import cn.wasu.community.community.model.User;
import cn.wasu.community.community.service.QuestionService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
        Integer totalCount=(int)questionMapper.countByExample(new QuestionExample());
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
        List<Question> questions=questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
        List<QuestionDTO>questionDTOList=new ArrayList<>();

        for (Question question:questions){
            User user=userMapper.selectByPrimaryKey(question.getCreator());
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
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount=(int)questionMapper.countByExample(questionExample);
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
        QuestionExample example=new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions=questionMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));
        List<QuestionDTO>questionDTOList=new ArrayList<>();

        for (Question question:questions){
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    @Override
    public QuestionDTO getById(Integer id) {
        Question question= questionMapper.selectByPrimaryKey(id);
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user=userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public void createOrUpdate(Question question) {
        //判断Id是否存在
        if(ObjectUtils.isEmpty(question.getId())){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else {
            //更新
            question.setGmtModified(System.currentTimeMillis());
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setDescription(question.getDescription());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                    .andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(updateQuestion, questionExample);
        }
    }
}
