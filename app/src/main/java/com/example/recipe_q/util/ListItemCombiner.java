package com.example.recipe_q.util;

import com.example.recipe_q.model.ListItem;
import com.example.recipe_q.model.ListItemCombined;

import java.util.ArrayList;
import java.util.List;

public class ListItemCombiner {
    public static List<ListItemCombined> combineSimilar(List<ListItem> raw) {
        List<ListItemCombined> combined = new ArrayList<>();
        if (raw.size() > 0) {
            ListItem item = raw.get(0);
            ArrayList group = new ArrayList<>();
            // noinspection unchecked
            group.add(item);

            String newName = item.getName();
            String oldName = newName;

            int size = raw.size();
            for (int i = 1; i < size; i++) {
                item = raw.get(i);
                newName = item.getName();
                if (newName.equals(oldName)) {
                    // noinspection unchecked
                    group.add(item);
                } else {
                    // noinspection unchecked
                    combined.add(new ListItemCombined(group));
                    oldName = newName;
                    group = new ArrayList<>();
                    // noinspection unchecked
                    group.add(item);
                }
            }
            if (size > 0) {
                // noinspection unchecked
                combined.add(new ListItemCombined(group));
            }
        }
        return combined;
    }
}
