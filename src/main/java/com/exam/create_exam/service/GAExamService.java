package com.exam.create_exam.service;

import com.exam.create_exam.javaBean.Paper;
import com.exam.create_exam.javaBean.RuleBean;

import java.util.List;
import java.util.Random;

/**
 * @author zzx
 */
public interface GAExamService {

    /**
     * 初始种群
     *
     * @param populationSize 种群规模
     * @param initFlag       初始化标志 true-初始化
     * @param rule           规则bean
     */
    public void Population(int populationSize, boolean initFlag, RuleBean rule);

    /**
     * 获取种群中最优秀个体
     *
     * @return
     */
    public Paper getFitness();

    /**
     * 获取种群中某个个体
     *
     * @param index
     * @return
     */
    public Paper getPaper(int index);

    /**
     * 设置种群中某个个体
     *
     * @param index
     * @param paper
     */
    public void setPaper(int index, Paper paper);

    /**
     * 返回种群规模
     *
     * @return
     */
    public int getLength();
}
