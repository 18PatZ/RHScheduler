package com.patrickzhong.rhscheduler;

import com.patrickzhong.rhscheduler.model.Group;
import com.patrickzhong.rhscheduler.schedulers.SequentialScheduler;

import java.util.*;

public class RHScheduler {

    static Random rand;
    static HashMap<String, Integer> orig = new HashMap<>();

    public static void main(String[] args){

        rand = new Random();

        List<Group> input = new ArrayList<>(Arrays.asList(
                Group.builder().name("Butterflies").duration(45).members(Arrays.asList("Ada", "Audrey")).build(),
                Group.builder().name("Prince Waltz").duration(60).members(Arrays.asList("Richard", "Sasha", "Jessica", "Patrick")).build(),
                Group.builder().name("River, Gerda, Angels").duration(30).members(Arrays.asList("Alina", "Jessica", "Arely", "Ada")).build(),
                Group.builder().name("Snow Palace").duration(60).members(Arrays.asList("Richard", "Harry", "Alina", "Jessica", "Katya", "Sasha")).build(),
                Group.builder().name("Angels").duration(45).members(Arrays.asList("Arely", "Ada")).build(),
                Group.builder().name("River").duration(30).members(Arrays.asList("Jessica", "Alina")).build(),
                Group.builder().name("River + Angels").duration(30).members(Arrays.asList("Jessica", "Alina", "Arely", "Ada")).build(),
                Group.builder().name("Room").duration(30).members(Arrays.asList("Richard", "Harry", "Alina", "Jessica")).build(),
                Group.builder().name("Crows").duration(60).members(Arrays.asList("Varvara", "Grace")).build(),
                Group.builder().name("Anticipation").duration(30).members(Arrays.asList("Richard", "Harry", "Jessica", "Alina")).build(),
                Group.builder().name("Palace").duration(60).members(Arrays.asList("Richard", "Sasha", "Jessica", "Patrick", "Grace", "Varvara", "Arely", "Ada")).build(),
                Group.builder().name("Ballerina and Soldier").duration(30).members(Arrays.asList("Patrick", "Alex", "Varvara", "Cynthia")).build(),
                Group.builder().name("Thumbelina and Elf").duration(30).members(Arrays.asList("Alex", "Leila", "Lera")).build(),
                Group.builder().name("Skater Rink").duration(30).members(Arrays.asList("Richard", "Harry", "Katya", "Sasha", "Jessica", "Alina", "Arely", "Ada")).build()));

        orig.put("Sasha", 525);
        orig.put("Alex", 60);
        orig.put("Harry", 435);
        orig.put("Cynthia", 30);
        orig.put("Grace", 150);
        orig.put("Arely", 465);
        orig.put("Katya", 435);
        orig.put("Lera", 30);
        orig.put("Patrick", 465);
        orig.put("Alina", 465);
        orig.put("Richard", 525);
        orig.put("Varvara", 180);
        orig.put("Leila", 30);
        orig.put("Audrey", 45);
        orig.put("Jessica", 525);
        orig.put("Ada", 570);


        p("---------");
        p("TRIAL 1");
        p("---------");
        go(input);

        for(int i = 2; i <= 10; i++){
            p("");
            p("---------");
            p("TRIAL "+i);
            p("---------");
            go(input = random(input));
        }
    }

    private static List<Group> random(List<Group> input){
        List<Group> other = new ArrayList<>();
        while(input.size() > 0)
            other.add(input.remove(rand.nextInt(input.size())));
        return other;
    }

    private static void go(List<Group> input){
        List<Group> schedule = new SequentialScheduler().getSchedule(input);

        HashMap<String, Integer> startTimes = new HashMap<>();
        HashMap<String, Integer> endTimes = new HashMap<>();

        p("SCHEDULE");

        int index = 0;
        for(Group group : schedule) {

            p(group);
            for(String member : group.getMembers()){
                startTimes.putIfAbsent(member, index);
                endTimes.put(member, index);
            }

            index++;
        }

        p("");
        p("STAY DURATION");
        for(String member : startTimes.keySet()){
            int duration = 0;
            for(int i = startTimes.get(member); i <= endTimes.get(member); i++)
                duration += schedule.get(i).getDuration();
            p("["+member+"] - "+format(duration)+" (Saves "+format(orig.get(member) - duration)+")");
        }
    }

    public static String format(int minutes){

        String sign = "";
        if(minutes < 0){
            sign = "-";
            minutes = - minutes;
        }

        int hours = minutes / 60;
        minutes -= hours * 60;
        String min = minutes+"";
        if(min.length() == 1)
            min += "0";
        return sign + hours+":"+min;
    }

    private static void p(Object obj){
        System.out.println(obj.toString());
    }

}
