package edu.njust.manager;

import edu.njust.entity.LogEntity;
import edu.njust.templates.Template;
import edu.njust.templates.TemplateMiner;

import java.util.*;

public class TemplatesManager {
    public static List<Template> templates = new ArrayList<>();

    public Template inferTemplate(String newMessage){
        LogEntity log = new LogEntity(newMessage);
        int len = log.getLogStr().length;
        Queue<Pair> candidates = new PriorityQueue<>((o1, o2) -> Double.compare(o2.score, o1.score));
        for(Template template: templates){
            if(template.splitTemplate.length != len){
                continue;
            }
            double score = template.getSimilarityScore(log);
            if(Double.compare(score, Template.Tc) < 0) continue;
            candidates.offer(new Pair(template.index, score));
        }
        if(!candidates.isEmpty()){
            int idx = candidates.peek().index;
            templates.get(idx).update(log);
            return templates.get(idx);
        }
        Template nTemplate = new TemplateMiner(templates.size(), newMessage);
        templates.add(nTemplate);
        return nTemplate;
    }

    class Pair{
        public int index;
        public double score;

        public Pair(int index, double score){
            this.index = index;
            this.score = score;
        }
    }
}
