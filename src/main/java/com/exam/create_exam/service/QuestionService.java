package com.exam.create_exam.service;

import com.exam.create_exam.javaBean.QuestionBean;
import com.exam.create_exam.mapper.AeTopicMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author zzx
 */
public class QuestionService {

    /**
     * 根据题目类型获取题目集
     *
     * @param type--题目类型
     * @param pointIds--知识点id
     * @return
     */
    public QuestionBean[] getQuestionArray(Integer type, List<String> pointIds) {
        return new QuestionBean[0];
    }
}
