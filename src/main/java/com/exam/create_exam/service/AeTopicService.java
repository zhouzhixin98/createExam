package com.exam.create_exam.service;

import com.exam.create_exam.entity.AeTopic;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.create_exam.javaBean.Paper;
import com.exam.create_exam.javaBean.QuestionBean;
import com.exam.create_exam.javaBean.RuleBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzx
 * @since 2021-11-22
 */
public interface AeTopicService extends IService<AeTopic> {
    /**
     * 根据题目类型获取题目集
     * @param type--题目类型
     * @param pointIds--知识点id
     * @return
     */
    List<AeTopic> getQuestionArray(Integer type, List<String> pointIds);

    /**
     * 从题库中获取和变异的题目类型一样分数相同的题目（不包含变异题目）
     * @param aeTopic
     * @return
     */
    List<AeTopic> getQuestionListWithOutSId(AeTopic aeTopic);

    /**
     * 根据科目年级获取知识点
     * @param subjects -- 科目
     * @param grade -- 年级
     * @return
     */
    List<AeTopic> selectList(Integer subjects,Integer grade);

    /**
     * 生成试卷主逻辑
     * @param rule
     * @param subject
     * @param grade
     * @return
     */
    Paper createExam(RuleBean rule, Integer subject, Integer grade);

    /**
     * test
     */
    void test1();



}
