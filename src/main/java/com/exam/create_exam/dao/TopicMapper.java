package com.exam.create_exam.dao;

import com.exam.create_exam.entity.AeTopic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TopicMapper {
    /**
     * 通过id查找题目
     * */
    AeTopic findTopicById(Long id);

    /**
     * 获取所有的年级信息
     * */
    List<Integer> getAllGrade();

    /**
     * 根据年级获取其他不同的教材
     * @param grade -- 对应年级
     * @return
     * */
    List<Integer> getVersionByGrade(Integer grade);

    /**
     * 根据年级教材科目获取章节
     * @param grade -- 对应年级
     * */
    List<String> getSectionByGraAndVerAndSubj(Integer grade,Integer version,Integer subject);

    /**
     * 根据年级教材科目章节获取对应题目
     * */
    List<AeTopic> getTopicByInfo(@Param("grade") Integer grade, @Param("version") Integer version, @Param("section") String[] section,
                                 @Param("subject")Integer subject);

    /**
     * 根据年级教材科目获取对应题目
     * */
    List<AeTopic> getTopicWithoutSection(@Param("grade") Integer grade,@Param("version") Integer version,
                                         @Param("subject")Integer subject);

    /**
     * 根据年级获取其他不同的知识点
     * @param grade -- 对应年级
     * */
    List<Integer> getKpIdByGrade(Integer grade);
    /**
     * 根据年级获取其他不同的题目类型
     * @param grade -- 对应年级
     * @return
     * */
    List<Integer> getTopicTypeByGrade(Integer grade);

    /**
     * 随机获取一道题目
     * */
    AeTopic getATopic();

    /***
     * 根据科目、年级、教材获取章节信息
     */
    List<String> getSection(Integer subject,Integer grade,Integer version);

    /***
     * 根据年级、科目、教材获取做题记录。
     */
    List<AeTopic> getTopicByGradeAndSubjectAndVersion(Integer grade,Integer subject,Integer version);

    /***
     * 根据年级、科目、教材获取题目id。
     */
    Long[] getTopicIdByGradeAndSubjectAndVersion(Integer grade,Integer subject,Integer version);

    /**
     * 根据题目id 获取知识点信息
     */
    String getKpIdByTopicId(Long topicId);

    /**
     * 根据知识点获取题目
     */
    AeTopic getATopicByKpId(String kpId);

    /**
     * 根据科目、年级、教材获取题目信息
     */
    List<AeTopic> getTopicBySubjectAndGradeAndVersion(@Param("subject") Integer subject,@Param("grade") Integer grade,@Param("version") Integer version,@Param("i")Integer i);

    /**
     * 修改题目质量信息
     */
    void updateTopicQuality(@Param("id")Long id,@Param("quality") Integer quality);

    /**
     * 根据年级、章节、知识点、教材获取题目
     * @param grade
     * @param version
     * @param knowledge
     * @param subject
     * @return
     */
    List<AeTopic> getTopicByKnoInfo(@Param("grade")Integer grade, @Param("version")Integer version, @Param("knowledge")String[] knowledge, @Param("subject")Integer subject);

}
