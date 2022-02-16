package edu.njust.templates;

import edu.njust.entity.LogEntity;

/*
* 日志模板的父类，提供所有日志模板共有的属性以及一些共有方法
*
* */
public abstract class Template {
    public int index;                 //index属性保存该日志模板的索引，用于快速从所有日志模板中定位特定的日志模板
    public String words;              //words属性保存该日志模板
    public int count;                 //counts属性保存该日志模板匹配了多少日志消息
    public String[] splitTemplate;    //splitTemplate属性保存将改日志信息按照空格划分出的一个个字符串，方便算法处理
    public static double Tc;          //相似度阈值
    public static int Tp;             //相同常量数量的阈值

    public abstract double getSimilarityScore(LogEntity message);

    public abstract void update(LogEntity message);
}
