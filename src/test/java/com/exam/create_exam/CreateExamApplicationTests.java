package com.exam.create_exam;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.create_exam.dao.TopicMapper;
import com.exam.create_exam.entity.AeTopic;
import com.exam.create_exam.mapper.AeTopicMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CreateExamApplicationTests {

    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private AeTopicMapper aeTopicMapper;

    @Test
    void contextLoads() {
        AeTopic topicById = topicMapper.findTopicById(18318702l);
        System.out.println(topicById);
    }

//    @Test
//    void selectTest(){
//        List<String> pointIds = new ArrayList<>();
////        pointIds.add("向心力,牛顿第二定律");
////        pointIds.add("利用万有引力定律研究天体运动");
////        pointIds.add("旋转的性质,等腰三角形的性质");
////        QueryWrapper<AeTopic> qw = new QueryWrapper<>();
////        qw.in("kp_id",pointIds);
////        qw.eq("topic_type","单选题");
////        List<AeTopic> aeTopics = aeTopicMapper.selectList(qw);
////        System.out.println(aeTopics);
////        System.out.println("-------------------------------");
////        System.out.println(aeTopics.size());
//    }

    @Test
     void listTest(){
        //获取测试知识点数据
        QueryWrapper<AeTopic> qw2 = new QueryWrapper<>();
        qw2.select("kp_id");
        qw2.eq("subjects",8);
        qw2.eq("grade",400);
        List<AeTopic> aeTopics = aeTopicMapper.selectList(qw2);
        System.out.println("结果集长度为" + aeTopics.size());
    }
}
