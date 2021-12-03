package com.exam.create_exam.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author zzx
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class AeTopic implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 题目id bigint 19位
     */
      private Long id;

    /**
     * 所属年级123456789
     */
    private Integer grade;

    /**
     * 教材类别
     */
    private Integer version;

    /**
     * 语数英政史地生物化
     */
    private Integer subjects;

    /**
     * 章节or大知识点
     */
    private String section;

    /**
     * 内容
     */
    private String address;

    /**
     * 0简单1中等2难
     */
    private Integer complexity;

    /**
     * 题目类型1客观题2主观题
     */
    private Integer type;

    /**
     * 知识点Id
     */
    private String kpId;

    /**
     * 题目类型填空题，选择题，多选题
     */
    private String topicType;

    /**
     * 被调用的次数
     */
    private Integer times;

    /**
     * 题目质量等级
     */
    private Integer quality;

    /**
     * 问题分数，由HR出卷时指定
     */
    @TableField(exist = false)
    private double score;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;


}
