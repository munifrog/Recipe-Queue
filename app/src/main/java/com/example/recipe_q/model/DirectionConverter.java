package com.example.recipe_q.model;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.recipe_q.util.Api.JSON_TAG_DIRECTION_GROUP_NAME;
import static com.example.recipe_q.util.Api.JSON_TAG_DIRECTION_GROUP_STEPS;
import static com.example.recipe_q.util.Api.JSON_TAG_DIRECTION_SINGLE_STEP;

public class DirectionConverter {
    public static String convertToString(@NonNull ArrayList<DirectionGroup> directionSteps) {
        JSONArray oneJsonGroupArray = new JSONArray();
        try {
            JSONObject oneJsonGroupObject;
            JSONObject oneJsonStepObject;
            JSONArray oneJsonStepArray;
            DirectionGroup currentGroup;
            Direction currentStep;
            ArrayList<Direction> steps;
            String groupName;
            String instruction;
            int numGroups = directionSteps.size();
            int numSteps;
            for (int i = 0; i < numGroups; i++) {
                currentGroup = directionSteps.get(i);
                groupName = currentGroup.getGroupName();
                steps = currentGroup.getGroupSteps();
                oneJsonStepArray = new JSONArray();
                numSteps = steps.size();
                for (int j = 0; j < numSteps; j++) {
                    currentStep = steps.get(j);
                    instruction = currentStep.getInstructionOriginal();
                    oneJsonStepObject = new JSONObject();
                    oneJsonStepObject.put(JSON_TAG_DIRECTION_SINGLE_STEP, instruction);
                    oneJsonStepArray.put(oneJsonStepObject);
                }
                oneJsonGroupObject = new JSONObject();
                oneJsonGroupObject.put(JSON_TAG_DIRECTION_GROUP_NAME, groupName);
                oneJsonGroupObject.put(JSON_TAG_DIRECTION_GROUP_STEPS, oneJsonStepArray);
                oneJsonGroupArray.put(oneJsonGroupObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return oneJsonGroupArray.toString();
    }

    public static ArrayList<DirectionGroup> convertToDirectionGroupArrayList(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            return convertToDirectionGroupArrayList(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static ArrayList<DirectionGroup> convertToDirectionGroupArrayList(JSONArray jsonArray) {
        ArrayList<DirectionGroup> directionGroups = new ArrayList<>();
        try {
            JSONObject currentJsonGroup;
            JSONArray currentJsonSteps;
            JSONObject currentJsonStep;
            ArrayList<Direction> oneGroup;
            DirectionGroup group;
            Direction oneDirection;
            String groupName;
            String oneInstruction;
            int numGroups = jsonArray.length();
            int numSteps;
            for (int i = 0; i < numGroups; i++) {
                currentJsonGroup = jsonArray.getJSONObject(i);
                currentJsonSteps = currentJsonGroup.getJSONArray(JSON_TAG_DIRECTION_GROUP_STEPS);
                oneGroup = new ArrayList<>();
                if (currentJsonSteps != null) {
                    numSteps = currentJsonSteps.length();
                    for (int j = 0; j < numSteps; j++) {
                        currentJsonStep = currentJsonSteps.getJSONObject(j);
                        oneInstruction = currentJsonStep.getString(JSON_TAG_DIRECTION_SINGLE_STEP);
                        oneDirection = new Direction(oneInstruction);
                        oneGroup.add(oneDirection);
                    }
                }
                groupName = currentJsonGroup.getString(JSON_TAG_DIRECTION_GROUP_NAME);
                group = new DirectionGroup(groupName, oneGroup);
                directionGroups.add(group);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return directionGroups;
    }
}
