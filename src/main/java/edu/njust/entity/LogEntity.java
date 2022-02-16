package edu.njust.entity;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

/*
* 日志实体类
*
* */
public class LogEntity {
    //原始的日志消息
    private String mes;
    //将日志消息按空格划分成一个个字符串，方便算法处理
    private String[] logStr;
    //日志消息的单词长度向量
    private RealVector v;

    public LogEntity(String mes){
        this.mes = mes;
        this.logStr = mes.split(" ");
        double[] wordsLen = new double[logStr.length];
        for(int i = 0;i < logStr.length;i++){
            wordsLen[i] = logStr[i].length();
        }
        //设置copyArray = true，复制一个新的double数组
        this.v = new ArrayRealVector(wordsLen, true);
    }

    public String[] getLogStr() {
        return logStr;
    }

    public RealVector getV() {
        return v;
    }
}
