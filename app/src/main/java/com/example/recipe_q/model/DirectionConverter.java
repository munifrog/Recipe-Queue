package com.example.recipe_q.model;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.recipe_q.util.Api.JSON_TAG_DIRECTION_GROUP_NAME;
import static com.example.recipe_q.util.Api.JSON_TAG_DIRECTION_GROUP_STEPS;
import static com.example.recipe_q.util.Api.JSON_TAG_DIRECTION_SINGLE_STEP;

public class DirectionConverter {
    @TypeConverter
    public static String convertToString(@NonNull List<DirectionGroup> directionSteps) {
        JSONArray oneJsonGroupArray = new JSONArray();
        try {
            JSONObject oneJsonGroupObject;
            JSONObject oneJsonStepObject;
            JSONArray oneJsonStepArray;
            DirectionGroup currentGroup;
            Direction currentStep;
            List<Direction> steps;
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

    @TypeConverter
    public static List<DirectionGroup> convertToDirectionGroupList(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            return convertToDirectionGroupList(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<DirectionGroup> convertToDirectionGroupList(JSONArray jsonArray) {
        List<DirectionGroup> directionGroups = new ArrayList<>();
        try {
            JSONObject currentJsonGroup;
            JSONArray currentJsonSteps;
            JSONObject currentJsonStep;
            List<Direction> oneGroup;
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
