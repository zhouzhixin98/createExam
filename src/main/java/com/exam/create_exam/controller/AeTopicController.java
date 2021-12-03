package com.exam.create_exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.create_exam.entity.AeTopic;
import com.exam.create_exam.javaBean.Paper;
import com.exam.create_exam.javaBean.RuleBean;
import com.exam.create_exam.mainTest.GA;
import com.exam.create_exam.service.AeTopicService;
import com.exam.create_exam.utils.Population;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzx
 * @since 2021-11-22
 */
@Controller
@RequestMapping("/aeTopic")
public class AeTopicController {

    private AeTopicService aeTopicService;

    public AeTopicController(AeTopicService aeTopicService) {
        this.aeTopicService = aeTopicService;
    }

    /**
     * 智能生成试卷
     * 后台策略：年级、科目、填空题的数量、单选题的数量、多选题的数量、问答题的数量，知识点选择（知识点非必需）
     * 然后从5星、4星，符合知识点的题目中进行选择。符合要求的题目不足，就选择理低星级的，或其它知识点的来填充。
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/createExam",method = RequestMethod.POST)
    public Paper getExamTopic(@RequestBody RuleBean rule,@RequestParam("subject")Integer subject,@RequestParam("grade")Integer grade){
        return aeTopicService.createExam(rule, subject, grade);
    }

}

