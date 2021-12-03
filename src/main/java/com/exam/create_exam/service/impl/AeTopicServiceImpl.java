package com.exam.create_exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.create_exam.entity.AeTopic;
import com.exam.create_exam.javaBean.Paper;
import com.exam.create_exam.javaBean.QuestionBean;
import com.exam.create_exam.javaBean.RuleBean;
import com.exam.create_exam.mainTest.GA;
import com.exam.create_exam.mapper.AeTopicMapper;
import com.exam.create_exam.service.AeTopicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.create_exam.utils.Population;
import com.exam.create_exam.utils.TopicType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzx
 * @since 2021-11-22
 */
@Service
public class AeTopicServiceImpl extends ServiceImpl<AeTopicMapper, AeTopic> implements AeTopicService {

    private AeTopicMapper aeTopicMapper;

    public AeTopicServiceImpl(AeTopicMapper aeTopicMapper) {
        this.aeTopicMapper = aeTopicMapper;
    }




    /**
     * 根据题目类型获取题目集
     *
     * @param type     --题目类型
     * @param pointIds --知识点id
     * @return
     */
    @Override
    public List<AeTopic> getQuestionArray(Integer type, List<String> pointIds) {
        String topicType;
        if (type == TopicType.SINGLE_NUM){
            topicType = "单选题";
        }else if (type == TopicType.COMPLETE_NUM){
            topicType = "填空题";
        }else if (type == TopicType.SUBJECTIVE_NUM){
            QueryWrapper<AeTopic> qw = new QueryWrapper<>();
            qw.in("kp_id",pointIds);
            //主观题字段为type--2
            qw.eq("type",2);
            List<AeTopic> aeTopics = aeTopicMapper.selectList(qw);
            return aeTopics;
        }else{
            //所选题目不合理
            return null;
        }
        QueryWrapper<AeTopic> qw = new QueryWrapper<>();
        qw.in("kp_id",pointIds);
        qw.eq("topic_type",topicType);
        List<AeTopic> aeTopics = aeTopicMapper.selectList(qw);
        return aeTopics;
    }

    /**
     * 从题库中获取和变异的题目类型一样分数相同的题目（不包含变异题目）
     *
     * @param aeTopic
     * @return
     */
    @Override
    public List<AeTopic> getQuestionListWithOutSId(AeTopic aeTopic) {
        QueryWrapper<AeTopic> qw = new QueryWrapper<>();
        qw.eq("topic_type",aeTopic.getTopicType());
        qw.ne("id",aeTopic.getId());
        return aeTopicMapper.selectList(qw);
    }

    /**
     * 根据科目年级获取知识点
     *
     * @param subjects -- 科目
     * @param grade    -- 年级
     * @return
     */
    @Override
    public List<AeTopic> selectList(Integer subjects, Integer grade) {
        //获取测试知识点数据
        QueryWrapper<AeTopic> qw2 = new QueryWrapper<>();
        qw2.eq("subjects",subjects);
        qw2.eq("grade",grade);
        qw2.select("kp_id");
        return aeTopicMapper.selectList(qw2);
    }

    /**
     * 生成试卷主逻辑
     *
     * @param rule
     * @param subject
     * @param grade
     * @return
     */
    @Override
    public Paper createExam(RuleBean rule, Integer subject, Integer grade) {
        Paper resultPaper = null;
        // 迭代计数器
        int count = 0;
        int runCount = 10;
        // 适应度期望值
        double expand = 0.8;
        //获取对应年级、科目下的知识点
        List<String> pointIds = new ArrayList<>();
        QueryWrapper<AeTopic> qw = new QueryWrapper<>();
        qw.select("kp_id").eq("subjects", subject).eq("grade", grade);
        //若没有选择知识点，则默认使用该年级科目下的所有知识点内容
        if (rule.getPointIds() == null){
            //获取测试知识点数据
            List<AeTopic> aeTopics1 = selectList(subject,grade);
            List<String> kpIds = new ArrayList<>();
            for (AeTopic aeTopic : aeTopics1) {
                kpIds.add(aeTopic.getKpId());
            }
            rule.setPointIds(kpIds);
        }
        // 可自己初始化组卷规则rule
        // 初始化种群
        Population population = new Population(this,20, true, rule);
        double adaptationDegree = population.getFitness().getAdaptationDegree();
//        System.out.println("初次适应度  " + adaptationDegree);
        if (Double.isNaN(adaptationDegree)){
            return null;
        }
        while (count < runCount && adaptationDegree < expand) {
            count++;
            population = GA.evolvePopulation(this,population, rule);
//            System.out.println("第 " + count + " 次进化，适应度为： " + adaptationDegree);
        }
//        System.out.println("进化次数： " + count);
//        System.out.println(adaptationDegree);
        resultPaper = population.getFitness();
        //检查生成试卷题目总数是否符合，若不符合，则返回错误信息
        int allTopicNum = rule.getSingleNum() + rule.getCompleteNum() + rule.getSubjectiveNum();
        if (resultPaper.getQuestionList().size() != allTopicNum){
            return null;
        }
        return resultPaper;
    }

    /**
     * test
     */
    @Override
    public void test1() {
        System.out.println("hello,world");
    }
}
