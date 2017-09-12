package com.patrickzhong.rhscheduler.model;

import com.patrickzhong.rhscheduler.RHScheduler;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class Group {

    String name;
    List<String> members = new ArrayList<>();
    int duration; // in minutes

    @Override
    public String toString() {
        return name+": "+members.toString() + " - "+ RHScheduler.format(duration);
    }

    @Override
    public int hashCode() {
        return -1;
    }

    @Override
    public boolean equals(Object obj) {
        return members.equals(((Group) obj).getMembers());
    }
}
