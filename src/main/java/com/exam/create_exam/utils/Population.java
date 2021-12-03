package com.exam.create_exam.utils;

import com.exam.create_exam.entity.AeTopic;
import com.exam.create_exam.javaBean.Global;
import com.exam.create_exam.javaBean.Paper;
import com.exam.create_exam.javaBean.RuleBean;
import com.exam.create_exam.service.AeTopicService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 种群，即多套试卷
 * @author zzx
 */
public class Population {
//    private Log log = LogFactory.getLog(Population.class);
    /**
     * 试卷集合
     */
    private Paper[] papers;

    /**
     * 初始种群
     *
     * @param populationSize 种群规模
     * @param initFlag       初始化标志 true-初始化
     * @param rule           规则bean
     */
    public Population(AeTopicService aeTopicService,int populationSize, boolean initFlag, RuleBean rule) {
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
//                        System.out.println("开始生成单选题");
                        String errorMsg = "单选题数量不够，组卷失败";
                        String s = generateQuestion(aeTopicService, 1, random, rule.getSingleNum(), rule.getSingleScore(), idString,
                                errorMsg, paper);
                        if (s.equals(errorMsg)){
                            System.out.println(errorMsg);
                            break;
                        }
                    }
                    // 填空题
                    if (rule.getCompleteNum() > 0) {
//                        System.out.println("开始生成填空题");
                        String errorMsg = "填空题数量不够，组卷失败";
                        String s = generateQuestion(aeTopicService, 2, random, rule.getCompleteNum(), rule.getCompleteScore(), idString,
                                errorMsg, paper);
                        if (s.equals(errorMsg)){
                            System.out.println(errorMsg);
                            break;
                        }
                    }
                    // 主观题
                    if (rule.getSubjectiveNum() > 0) {
//                        System.out.println("开始生成主观题");
                        String errorMsg = "主观题数量不够，组卷失败";
                        String s = generateQuestion(aeTopicService, 3, random, rule.getSubjectiveNum(), rule.getSubjectiveScore(), idString,
                                errorMsg, paper);
                        if (s.equals(errorMsg)){
                            System.out.println(errorMsg);
                            break;
                        }
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

    private String generateQuestion(AeTopicService aeTopicService,int type, Random random, int qustionNum, double score, List<String> idString,
                                  String errorMsg, Paper paper) {
        List<AeTopic> singleArray = aeTopicService.getQuestionArray(type, idString);
        if (singleArray.size() < qustionNum) {
            System.out.println(errorMsg);
            return errorMsg;
        }
        AeTopic tmpQuestion;
        for (int j = 0; j < qustionNum; j++) {
            int index = random.nextInt(singleArray.size() - j);
            // 初始化分数
            singleArray.get(index).setScore(score);
            //向试卷添加题目
            paper.addQuestion(singleArray.get(index));
            // 保证不会重复添加试题
            tmpQuestion = singleArray.get(singleArray.size() - j - 1);
            singleArray.set(singleArray.size() - j - 1,singleArray.get(index));
            singleArray.set(index,tmpQuestion);
        }
        return "开始添加题目" + type;
    }

    /**
     * 获取种群中最优秀个体
     *
     * @return
     */
    public Paper getFitness() {
        Paper paper = papers[0];
        for (int i = 1; i < papers.length; i++) {
            if (paper.getAdaptationDegree() < papers[i].getAdaptationDegree()) {
                paper = papers[i];
            }
        }
        return paper;
    }

    public Population(int populationSize) {
        papers = new Paper[populationSize];
    }

    /**
     * 获取种群中某个个体
     *
     * @param index
     * @return
     */
    public Paper getPaper(int index) {
        return papers[index];
    }

    /**
     * 设置种群中某个个体
     *
     * @param index
     * @param paper
     */
    public void setPaper(int index, Paper paper) {
        papers[index] = paper;
    }

    /**
     * 返回种群规模
     *
     * @return
     */
    public int getLength() {
        return papers.length;
    }

}
