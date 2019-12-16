package com.example.recipe_q.model;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ListItemCombined {
    private static final String LIST_SEPARATOR = ", ";

    private long [] mIndices;
    private String mNameCombined;
    private String mOriginCombined;
    private String mQuantityCombined;
    private long mTimestampCombined;
    private String mUnitCombined;

    // Assumption: to be grouped together, the name and timestamp should match
    ListItemCombined (List<ListItem> grouping) {
        ListItem current = grouping.get(0);

        StringBuilder quantityBuilder = new StringBuilder();
        StringBuilder unitBuilder = new StringBuilder();
        Set<String> originSet = new HashSet<>();
        StringBuilder originBuilder = new StringBuilder();

        int groupingSize = grouping.size();
        mIndices = new long [groupingSize];

        mNameCombined = current.getName();
        originSet.add(current.getSourceName());
        mTimestampCombined = current.getTimestamp();
        mIndices[0] = current.getIdentifier();

        float currentQuantity = current.getQuantity();
        String currentUnit = current.getUnit();

        float quantitySameUnit = currentQuantity;
        String previousUnit = currentUnit;

        for (int i = 1; i < groupingSize; i++) {
            current = grouping.get(i);
            mIndices[i] = current.getIdentifier();

            currentQuantity = current.getQuantity();
            currentUnit = current.getUnit();

            originSet.add(current.getSourceName());

            if (currentUnit.equals(previousUnit)) {
                // Add quantities together; units (since same) theoretically merge too
                quantitySameUnit += currentQuantity;
            } else {
                if (quantityBuilder.length() != 0) {
                    quantityBuilder.append(LIST_SEPARATOR);
                }
                quantityBuilder.append(quantitySameUnit);
                quantitySameUnit = currentQuantity;

                if (unitBuilder.length() != 0) {
                    unitBuilder.append(LIST_SEPARATOR);
                }
                unitBuilder.append(previousUnit);
                previousUnit = currentUnit;
            }
        }
        if (groupingSize > 0) {
            if (quantityBuilder.length() != 0) {
                quantityBuilder.append(LIST_SEPARATOR);
            }
            quantityBuilder.append(quantitySameUnit);

            if (unitBuilder.length() != 0) {
                unitBuilder.append(LIST_SEPARATOR);
            }
            unitBuilder.append(previousUnit);
        }

        Iterator iter = originSet.iterator();
        String currentOrigin;
        while (iter.hasNext()) {
            currentOrigin = (String) iter.next();
            if (originBuilder.length() != 0) {
                originBuilder.append(LIST_SEPARATOR);
            }
            originBuilder.append(currentOrigin);
        }

        mOriginCombined = originBuilder.toString();
        mQuantityCombined = quantityBuilder.toString();
        mUnitCombined = unitBuilder.toString();
    }

    public String getName() { return mNameCombined; }
    public String getQuantity() { return mQuantityCombined; }
    public String getSourceName() { return mOriginCombined; }
    public long getTimestamp() { return mTimestampCombined; }
    public void resetTimestamp() { mTimestampCombined = 0; }
    public void setTimestamp() { mTimestampCombined = Calendar.getInstance().getTimeInMillis(); }
    public String getUnit() { return mUnitCombined; }
    public long [] getIndices() { return mIndices; }
}
