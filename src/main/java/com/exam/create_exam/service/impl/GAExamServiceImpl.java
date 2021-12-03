package com.exam.create_exam.service.impl;

import com.exam.create_exam.entity.AeTopic;
import com.exam.create_exam.javaBean.Global;
import com.exam.create_exam.javaBean.Paper;
import com.exam.create_exam.javaBean.RuleBean;
import com.exam.create_exam.service.AeTopicService;
import com.exam.create_exam.service.GAExamService;
import com.exam.create_exam.utils.Population;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class GAExamServiceImpl implements GAExamService {

    private Log log = LogFactory.getLog(Population.class);
    /**
     * 试卷集合
     */
    private Paper[] papers;

    @Autowired
    private AeTopicService aeTopicService;

    /**
     * 初始种群
     *
     * @param populationSize 种群规模
     * @param initFlag       初始化标志 true-初始化
     * @param rule           规则bean
     */
    @Override
    public void Population(int populationSize, boolean initFlag, RuleBean rule) {
        papers = new Paper[populationSize];
        if (initFlag) {
            Paper paper;
            Random random = new Random();
            for (int i = 0; i < populationSize; i++) {
                paper = new Paper();
                paper.setId(i + 1);
                while (paper.getTotalScore() != rule.getTotalMark()) {
                    paper.getQuestionList().clear();
                    List<String> idString = rule.getPointIds();
                    // 单选题
                    if (rule.getSingleNum() > 0) {
                        generateQuestion(1, random, rule.getSingleNum(), rule.getSingleScore(), idString,
                                "单选题数量不够，组卷失败", paper);
                    }
                    // 填空题
                    if (rule.getCompleteNum() > 0) {
                        generateQuestion(2, random, rule.getCompleteNum(), rule.getCompleteScore(), idString,
                                "填空题数量不够，组卷失败", paper);
                    }
                    // 主观题
                    if (rule.getSubjectiveNum() > 0) {
                        generateQuestion(3, random, rule.getSubjectiveNum(), rule.getSubjectiveScore(), idString,
                                "主观题数量不够，组卷失败", paper);
                    }
                }
                // 计算试卷知识点覆盖率
                paper.setKpCoverage(rule);
                // 计算试卷适应度
                paper.setAdaptationDegree(rule, Global.KP_WEIGHT, Global.DIFFCULTY_WEIGHt);
                papers[i] = paper;
            }
        }
    }

    /**
     * 获取种群中最优秀个体
     *
     * @return
     */
    @Override
    public Paper getFitness() {
        Paper paper = papers[0];
        for (int i = 1; i < papers.length; i++) {
            if (paper.getAdaptationDegree() < papers[i].getAdaptationDegree()) {
                paper = papers[i];
            }
        }
        return paper;
    }

    /**
     * 获取种群中某个个体
     *
     * @param index
     * @return
     */
    @Override
    public Paper getPaper(int index) {
        return papers[index];
    }

    /**
     * 设置种群中某个个体
     *
     * @param index
     * @param paper
     */
    @Override
    public void setPaper(int index, Paper paper) {
        papers[index] = paper;
    }

    /**
     * 返回种群规模
     *
     * @return
     */
    @Override
    public int getLength() {
        return papers.length;
    }

    private void generateQuestion(int type, Random random, int qustionNum, double score, List<String> idString,
                                  String errorMsg, Paper paper) {
//        QuestionBean[] singleArray = QuestionService.getQuestionArray(type, idString);
        List<AeTopic> singleArray = aeTopicService.getQuestionArray(type, idString);
        if (singleArray.size() < qustionNum) {
            return;
        }
        AeTopic tmpQuestion;
        for (int j = 0; j < qustionNum; j++) {
//            int index = random.nextInt(singleArray.length - j);
            int index = random.nextInt(singleArray.size() - j);
            // 初始化分数
//            singleArray[index].setScore(score);
            singleArray.get(index).setScore(score);
//            paper.addQuestion(singleArray[index]);
            paper.addQuestion(singleArray.get(index));
            // 保证不会重复添加试题
//            tmpQuestion = singleArray[singleArray.length - j - 1];
            tmpQuestion = singleArray.get(singleArray.size() - j - 1);
//            singleArray[singleArray.length - j - 1] = singleArray[index];
            singleArray.set(singleArray.size() - j - 1,singleArray.get(index));
//            singleArray[index] = tmpQuestion;
            singleArray.set(index,tmpQuestion);
        }
    }
}
