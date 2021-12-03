package com.exam.create_exam;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.create_exam.entity.AeTopic;
import com.exam.create_exam.javaBean.Paper;
import com.exam.create_exam.javaBean.RuleBean;
import com.exam.create_exam.mainTest.GA;
import com.exam.create_exam.mapper.AeTopicMapper;
import com.exam.create_exam.service.AeTopicService;
import com.exam.create_exam.utils.Population;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MainTest {

    @Autowired
    private AeTopicMapper aeTopicMapper;
    @Autowired
    private AeTopicService aeTopicService;

    @Test
    void mainTest(){
        Paper resultPaper = null;
        // 迭代计数器
        int count = 0;
        int runCount = 100;
        // 适应度期望值
        double expand = 0.98;
        //获取对应年级、科目下的知识点
        List<String> pointIds = new ArrayList<>();
        QueryWrapper<AeTopic> qw = new QueryWrapper<>();
        qw.select("kp_id").eq("subjects", 8).eq("grade", 400);
//        List<AeTopic> aeTopics = aeTopicMapper.selectList(qw);
        //获取测试知识点数据
        QueryWrapper<AeTopic> qw2 = new QueryWrapper<>();
        qw2.eq("subjects",9);
        qw2.eq("grade",400);
        qw2.select("kp_id");
        List<AeTopic> aeTopics1 = aeTopicMapper.selectList(qw2);
        List<String> kpIds = new ArrayList<>();
        for (AeTopic aeTopic : aeTopics1) {
            kpIds.add(aeTopic.getKpId());
        }
        // 可自己初始化组卷规则rule
        Date date = new Date(System.currentTimeMillis());
        RuleBean rule = new RuleBean(1L, 1L,100,2.0,10,5.0,5,5.0,2,12.5,kpIds,date);
        if (rule != null) {
            // 初始化种群
            Population population = new Population(aeTopicService,20, true, rule);
            System.out.println("初次适应度  " + population.getFitness().getAdaptationDegree());
              while (count < runCount && population.getFitness().getAdaptationDegree() < expand) {
                  count++;
                  population = GA.evolvePopulation(aeTopicService,population, rule);
                  System.out.println("第 " + count + " 次进化，适应度为： " + population.getFitness().getAdaptationDegree());
              }
              System.out.println("进化次数： " + count);
              System.out.println(population.getFitness().getAdaptationDegree());
              resultPaper = population.getFitness();
        }
          System.out.println(resultPaper);
    }

    @Test
    void selectKnowledgeTest(){
        QueryWrapper<AeTopic> qw2 = new QueryWrapper<>();
        qw2.eq("subjects",9);
        qw2.eq("grade",400);
        qw2.select("kp_id");
        List<AeTopic> aeTopics1 = aeTopicMapper.selectList(qw2);
        List<String> kpIds = new ArrayList<>();
        for (AeTopic aeTopic : aeTopics1) {
            kpIds.add(aeTopic.getKpId());
        }
        System.out.println(kpIds.size());
    }
}
