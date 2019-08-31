package cn.wasu.community.community.mapper;

import cn.wasu.community.community.model.Question;
import cn.wasu.community.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
    int incView(@Param("record")Question record);
}