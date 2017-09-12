package com.patrickzhong.rhscheduler.schedulers;

import com.patrickzhong.rhscheduler.model.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SequentialScheduler {


    public List<Group> getSchedule(List<Group> groups){

        List<Group> schedule = new ArrayList<>();

        for(Group group : groups){

            int slot = getOptimalSlot(schedule, group);
            if(slot >= schedule.size())
                schedule.add(group);
            else
                schedule.add(slot, group);

        }

        return schedule;

    }

    private int getOptimalSlot(List<Group> schedule, Group group){

        int min = Integer.MAX_VALUE;
        int slot = -1;

        String s = "";

        for(int i = 0; i <= schedule.size(); i++) {
            int weight = getBaseWeight(schedule, group, i) + getSplitWeight(schedule, group, i);

            if(weight < min){
                min = weight;
                slot = i;
            }

            s += weight+" ";
        }

        //System.err.println(group +"--- "+s);

        return slot == -1 ? schedule.size() : slot;
    }

    private int getBaseWeight(List<Group> schedule, Group group, int slot){

        // calculated off total distance of elements

        int weight = 0;

        for(String search : group.getMembers()){
            int minDist = Integer.MAX_VALUE;
            for(int i = 0; i < schedule.size(); i++){

                int sum = 0;

                int left = i + 1;
                int right = slot - 1;

                if(i > slot){
                    left = slot;
                    right = i-1;
                }

                for(int j = left; j <= right; j++)
                    sum += schedule.get(j).getDuration();

                //int dist = Math.abs(slot - i);
                if(sum < minDist && schedule.get(i).getMembers().contains(search))
                    minDist = sum;
            }

            if(minDist != Integer.MAX_VALUE)
                weight += minDist;
        }

        return weight;

    }

    private int getSplitWeight(List<Group> schedule, Group group, int i){
        if(i <= 0 || i >= schedule.size()) return 0;

        Group g1 = schedule.get(i - 1);
        Group g2 = schedule.get(i);

        int weight = 0;
        for(String member : g1.getMembers())
            if (g2.getMembers().contains(member) && !group.getMembers().contains(member)) // inconvenienced member
                weight += group.getDuration();

        return weight;
    }


}
