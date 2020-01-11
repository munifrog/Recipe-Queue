package com.example.recipe_q.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ListItemCombined implements Parcelable {
    private static final String LIST_SEPARATOR = ", ";

    private long [] mIndices;
    private String mNameCombined;
    private String mOriginCombined;
    private String mQuantityCombined;
    private long mTimestampCombined;
    private String mUnitCombined;

    // Assumption: to be grouped together, the name and timestamp should match
    public ListItemCombined (List<ListItem> grouping) {
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
    public void setName(String name) { this.mNameCombined = name; }
    public String getQuantity() { return mQuantityCombined; }
    public void setQuantity(String quantity) { this.mQuantityCombined = quantity; }
    public String getSourceName() { return mOriginCombined; }
    public void setOrigin(String origin) { this.mOriginCombined = origin; }
    public long getTimestamp() { return mTimestampCombined; }
    public void resetTimestamp() { mTimestampCombined = 0; }
    public void setTimestamp() { mTimestampCombined = Calendar.getInstance().getTimeInMillis(); }
    public void setTimestamp(long time) { mTimestampCombined = time; }
    public String getUnit() { return mUnitCombined; }
    public void setUnit(String unit) { this.mUnitCombined = unit; }
    public long [] getIndices() { return mIndices; }
    public void setIndices(long[] indices) { this.mIndices = indices; }

    public static final Parcelable.Creator<ListItemCombined> CREATOR = new Parcelable.Creator<ListItemCombined>() {
        @Override
        public ListItemCombined createFromParcel(Parcel source) {
            return new ListItemCombined(source);
        }

        @Override
        public ListItemCombined [] newArray(int size) {
            return new ListItemCombined[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLongArray(mIndices);
        dest.writeString(mNameCombined);
        dest.writeString(mOriginCombined);
        dest.writeString(mQuantityCombined);
        dest.writeLong(mTimestampCombined);
        dest.writeString(mUnitCombined);
    }

    private ListItemCombined(Parcel parcel) {
        parcel.readLongArray(mIndices);
        setName(parcel.readString());
        setOrigin(parcel.readString());
        setQuantity(parcel.readString());
        setTimestamp(parcel.readLong());
        setUnit(parcel.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
