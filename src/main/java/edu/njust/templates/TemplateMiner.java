package edu.njust.templates;

import edu.njust.entity.LogEntity;
import edu.njust.util.VectorUtil;
import org.apache.commons.math3.linear.RealVector;

public class TemplateMiner extends Template{
    //wordLens属性保存日志模板的单词长度向量
    public RealVector vc;

    public TemplateMiner(int index, String words){
        this.index = index;
        this.words = words;
        this.count = 1;
        this.splitTemplate = words.split(" ");
        double[] wordLens = new double[splitTemplate.length];
        for(int i = 0;i < wordLens.length;i++){
            wordLens[i] = splitTemplate[i].length();
        }
        vc = VectorUtil.arrayToVector(wordLens, true);
    }


    @Override
    public double getSimilarityScore(LogEntity message) {
        String[] splitNewMessage = message.getLogStr();
        if(!splitTemplate[0].equals(splitNewMessage[0])){
            return 0.0d;
        }
        float accuracyScore = getAccuracyScore(message);
        if(accuracyScore == 1){
            return 1;
        }
        if(countSameWordPositions(message) < Tp){
            return 0;
        }
        double cosineScore = getSimilarityScoreCosine(message);
        return cosineScore;
    }

    /**
    * @Description: 计算日志模板与新的日志消息之间单词长度向量的余弦相似度
    * @Param:  newMessage: 新的日志消息
    * @return:  cosine: 余弦相似度
    * @Author: Liu ZhiTian
    * @Date: 2022/2/13
    */
    public double getSimilarityScoreCosine(LogEntity message){
        RealVector v = message.getV();
        return vc.cosine(v);
    }

    /** 
    * @Description:  计算日志模板与新的日志消息分类的准确率(通配符*可以匹配任意的单词)
    * @Param:  newMessage: 新的日志消息样本
    * @return:  准确率
    * @Author: Liu ZhiTian 
    * @Date: 2022/2/13 
    */ 
    public float getAccuracyScore(LogEntity message){
        String[] splitNewMessage = message.getLogStr();
        int cnt = 0, n = splitTemplate.length;
        //计算准确率
        for(int i = 0;i < n;i++){
            if(splitTemplate[i].equals("*")||splitTemplate[i].equals(splitNewMessage[i])){
                cnt++;
            }
        }
        return (1.0f * cnt) / (1.0f * n);
    }

    public int countSameWordPositions(LogEntity message){
        int c = 0;
        String[] splitNewMessage = message.getLogStr();
        for(int i = 0;i < splitTemplate.length;i++){
            if(splitTemplate[i].equals(splitNewMessage[i])){
                c++;
            }
        }
        return c;
    }

    @Override
    public void update(LogEntity message) {
        this.count++;
        StringBuilder sb = new StringBuilder();
        String[] splitNewMessage = message.getLogStr();
        int n = splitNewMessage.length;

        for(int i = 0;i < n;i++) {
            if (!splitTemplate[i].equals(splitNewMessage[i])) {
                splitTemplate[i] = new String("*");
            }
            if(i == n - 1){
                sb.append(splitTemplate[i]);
            }else{
                sb.append(splitTemplate[i] + " ");
            }
        }
        this.words = sb.toString();
    }
}
