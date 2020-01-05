package com.example.recipe_q.model;

import java.util.List;

public class DirectionGroup {
    private String mGroupName;
    private List<Direction> mGroupSteps;

    public DirectionGroup (
            String groupName,
            List<Direction> groupDirections
    ) {
        mGroupName = groupName;
        mGroupSteps = groupDirections;
    }

    public String getGroupName() { return mGroupName; }
    public void setGroupName(String groupName) { this.mGroupName = groupName; }

    public List<Direction> getGroupSteps() { return mGroupSteps; }
    public void setGroupSteps(List<Direction> groupSteps) {
        this.mGroupSteps = groupSteps;
    }
}
