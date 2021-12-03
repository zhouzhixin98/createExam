//package com.exam.create_exam.mainTest;
//
//import com.exam.create_exam.javaBean.Paper;
//import com.exam.create_exam.javaBean.RuleBean;
//import com.exam.create_exam.utils.Population;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MainTest {
//    public static void main(String[] args) {
//        Paper resultPaper = null;
//        // 迭代计数器
//        int count = 0;
//        int runCount = 100;
//        // 适应度期望值
//        double expand = 0.98;
//        // 可自己初始化组卷规则rule
//        List<String> pointIds = new ArrayList<>();
//        RuleBean rule = new RuleBean(1,1,100,2,10,4,5,5,2,17.5,);
//        if (rule != null) {
//            // 初始化种群
//            Population population = new Population(20, true, rule);
//            System.out.println("初次适应度  " + population.getFitness().getAdaptationDegree());
//            while (count < runCount && population.getFitness().getAdaptationDegree() < expand) {
//                count++;
//                population = GA.evolvePopulation(population, rule);
//                System.out.println("第 " + count + " 次进化，适应度为： " + population.getFitness().getAdaptationDegree());
//            }
//            System.out.println("进化次数： " + count);
//            System.out.println(population.getFitness().getAdaptationDegree());
//            resultPaper = population.getFitness();
//        }
//        System.out.println(resultPaper);
//    }
//}
