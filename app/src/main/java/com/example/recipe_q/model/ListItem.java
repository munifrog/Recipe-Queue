package com.example.recipe_q.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class ListItem {
    @Ignore private static final String CUSTOM_SOURCE_NAME = "";
    @Ignore private static final long CUSTOM_SOURCE_IDENTIFIER = 0;
    @Ignore private static final long ABSENT_TIMESTAMP = 0;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    private long mIdentifier;

    @ColumnInfo(name = "item_name")
    private String mName;

    @ColumnInfo(name = "item_quantity")
    private float mQuantity;

    @ColumnInfo(name = "item_unit")
    private String mUnit;

    @ColumnInfo(name = "item_swiped")
    private long mTimestamp;

    @ColumnInfo(name = "source_id")
    private long mSourceId;

    @ColumnInfo(name = "source_name")
    private String mSourceName;

    public ListItem(
            String name,
            String unit,
            float quantity,
            long timestamp,
            String sourceName,
            long sourceId
    ) {
        mTimestamp = timestamp;
        mName = name;
        mUnit = unit;
        mQuantity = quantity;
        mSourceName = sourceName;
        mSourceId = sourceId;
    }

    @Ignore
    public ListItem(
            String name,
            String unit,
            float quantity,
            String sourceName,
            long sourceId
    ) {
        this(
                name,
                unit,
                quantity,
                ABSENT_TIMESTAMP,
                sourceName,
                sourceId
        );
    }

    @Ignore
    public ListItem(
            String name,
            String unit,
            float quantity,
            String sourceName
    ) {
        this(
                name,
                unit,
                quantity,
                sourceName,
                CUSTOM_SOURCE_IDENTIFIER
        );
    }

    @Ignore
    public ListItem(
            String name,
            String unit,
            float quantity
    ) {
        this(
                name,
                unit,
                quantity,
                CUSTOM_SOURCE_NAME
        );
    }

    public long getIdentifier() { return mIdentifier; }
    public void setIdentifier(long mIdentifier) { this.mIdentifier = mIdentifier; }

    public String getName() { return mName; }
    public void setName(String name) { this.mName = name; }

    public float getQuantity() { return mQuantity; }
    public void setQuantity(float quantity) { this.mQuantity = quantity; }

    public long getTimestamp() { return mTimestamp; }
    public void setTimestamp(long timestamp) { this.mTimestamp = timestamp; }

    public String getUnit() { return mUnit; }
    public void setUnit(String unit) { this.mUnit = unit; }

    public long getSourceId() { return mSourceId; }
    public void setSourceId(long sourceId) { this.mSourceId = sourceId; }

    public String getSourceName() { return mSourceName; }
    public void setSourceName(String sourceName) { this.mSourceName = sourceName; }
}
