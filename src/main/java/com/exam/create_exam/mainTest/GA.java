package com.exam.create_exam.mainTest;

import com.exam.create_exam.entity.AeTopic;
import com.exam.create_exam.javaBean.Global;
import com.exam.create_exam.javaBean.Paper;
import com.exam.create_exam.javaBean.RuleBean;
import com.exam.create_exam.service.AeTopicService;
import com.exam.create_exam.utils.Population;
import java.util.List;

/**
 * 遗传算法组卷实现类
 * @author zzx
 */
public class GA {


    /**
     * 变异概率
     */
    private static final double mutationRate = 0.085;
    /**
     * 精英主义
     */
    private static final boolean elitism = true;
    /**
     * 淘汰数组大小
     */
    private static final int tournamentSize = 5;

    // 进化种群
    public static Population evolvePopulation(AeTopicService aeTopicService,Population pop, RuleBean rule) {
        Population newPopulation = new Population(pop.getLength());
        int elitismOffset;
        // 精英主义
        if (elitism) {
            elitismOffset = 1;
            // 保留上一代最优秀个体
            Paper fitness = pop.getFitness();
            fitness.setId(0);
            newPopulation.setPaper(0, fitness);
        }
        // 种群交叉操作，从当前的种群pop来创建下一代种群newPopulation
        for (int i = elitismOffset; i < newPopulation.getLength(); i++) {
            // 较优选择parent
            Paper parent1 = select(pop);
            Paper parent2 = select(pop);
            while (parent2.getId() == parent1.getId()) {
                parent2 = select(pop);
            }
            // 交叉
            Paper child = crossover(aeTopicService,parent1, parent2, rule);
            child.setId(i);
            newPopulation.setPaper(i, child);
        }
        // 种群变异操作
        Paper tmpPaper;
        for (int i = elitismOffset; i < newPopulation.getLength(); i++) {
            tmpPaper = newPopulation.getPaper(i);
            mutate(aeTopicService,tmpPaper);
            // 计算知识点覆盖率与适应度
            tmpPaper.setKpCoverage(rule);
            tmpPaper.setAdaptationDegree(rule, Global.KP_WEIGHT, Global.DIFFCULTY_WEIGHt);
        }
        return newPopulation;
    }

    /**
     * 交叉算子
     *
     * @param parent1
     * @param parent2
     * @return
     */
    public static Paper crossover(AeTopicService aeTopicService,Paper parent1, Paper parent2, RuleBean rule) {
        Paper child = new Paper(parent1.getQuestionSize());
        int s1 = (int) (Math.random() * parent1.getQuestionSize());
        int s2 = (int) (Math.random() * parent1.getQuestionSize());

        // parent1的startPos endPos之间的序列，会被遗传到下一代
        int startPos = s1 < s2 ? s1 : s2;
        int endPos = s1 > s2 ? s1 : s2;
        for (int i = startPos; i < endPos; i++) {
            child.saveQuestion(i, parent1.getQuestion(i));
        }

        // 继承parent2中未被child继承的question
        // 防止出现重复的元素
        String idString = rule.getPointIds().toString();
        for (int i = 0; i < startPos; i++) {
            if (!child.containsQuestion(parent2.getQuestion(i))) {
                child.saveQuestion(i, parent2.getQuestion(i));
            } else {
                int type = getTypeByIndex(i, rule);
                // getQuestionArray()用来选择指定类型和知识点的试题数组
                List<AeTopic> singleArray = aeTopicService.getQuestionArray(type, rule.getPointIds());
//                QuestionBean[] singleArray = QuestionService.getQuestionArray(type, idString.substring(1, idString
//                        .indexOf("]")));
//                child.saveQuestion(i, singleArray[(int) (Math.random() * singleArray.length)]);
//                System.out.println(singleArray.size());
//                System.out.println(Math.random() * singleArray.size());
                child.saveQuestion(i,singleArray.get((int) (Math.random() * singleArray.size())));
            }
        }
        for (int i = endPos; i < parent2.getQuestionSize(); i++) {
            if (!child.containsQuestion(parent2.getQuestion(i))) {
                child.saveQuestion(i, parent2.getQuestion(i));
            } else {
                int type = getTypeByIndex(i, rule);
//                QuestionBean[] singleArray = QuestionService.getQuestionArray(type, idString.substring(1, idString
//                        .indexOf("]")));
                List<AeTopic> singleArray = aeTopicService.getQuestionArray(type, rule.getPointIds());
//                child.saveQuestion(i, singleArray[(int) (Math.random() * singleArray.length)]);
//                System.out.println(singleArray.size());
//                System.out.println(Math.random() * singleArray.size());
                child.saveQuestion(i, singleArray.get((int) (Math.random() * singleArray.size())));
            }
        }
        return child;
    }

    private static int getTypeByIndex(int index, RuleBean rule) {
        int type = 0;
        // 单选
        if (index < rule.getSingleNum()) {
            type = 1;
        } else if (index < rule.getSingleNum() + rule.getCompleteNum()) {
            // 填空
            type = 2;
        } else {
            // 主观
            type = 3;
        }
        return type;
    }

    /**
     * 突变算子 每个个体的每个基因都有可能突变
     *
     * @param paper
     */
    public static void mutate(AeTopicService aeTopicService,Paper paper) {
        AeTopic tmpQuestion;
        List<AeTopic> list;
        int index;
        for (int i = 0; i < paper.getQuestionSize(); i++) {
            if (Math.random() < mutationRate) {
                // 进行突变，第i道
                tmpQuestion = paper.getQuestion(i);
                // 从题库中获取和变异的题目类型一样分数相同的题目（不包含变异题目）
                list = aeTopicService.getQuestionListWithOutSId(tmpQuestion);
                if (list.size() > 0) {
                    // 随机获取一道
                    index = (int) (Math.random() * list.size());
                    // 设置分数
                    list.get(index).setScore(tmpQuestion.getScore());
                    paper.saveQuestion(i, list.get(index));
                }
            }
        }
    }

    /**
     * 选择算子
     *
     * @param population
     */
    private static Paper select(Population population) {
        Population pop = new Population(tournamentSize);
        for (int i = 0; i < tournamentSize; i++) {
            pop.setPaper(i, population.getPaper((int) (Math.random() * population.getLength())));
        }
        return pop.getFitness();
    }
}
