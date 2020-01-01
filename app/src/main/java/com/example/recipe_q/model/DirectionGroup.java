package com.example.recipe_q.model;

import java.util.ArrayList;

public class DirectionGroup {
    private String mGroupName;
    private ArrayList<Direction> mGroupSteps;

    public DirectionGroup (
            String groupName,
            ArrayList<Direction> groupDirections
    ) {
        mGroupName = groupName;
        mGroupSteps = groupDirections;
    }

    public String getGroupName() { return mGroupName; }
    public void setGroupName(String groupName) { this.mGroupName = groupName; }

    public ArrayList<Direction> getGroupSteps() { return mGroupSteps; }
    public void setGroupSteps(ArrayList<Direction> groupSteps) {
        this.mGroupSteps = groupSteps;
    }
}
