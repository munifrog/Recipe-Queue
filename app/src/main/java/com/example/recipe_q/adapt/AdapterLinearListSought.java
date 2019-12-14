package com.example.recipe_q.adapt;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe_q.R;
import com.example.recipe_q.model.ListItemCombined;
import com.example.recipe_q.model.ViewModel;

import java.util.List;

public class AdapterLinearListSought extends RecyclerView.Adapter<AdapterLinearListSought.ListItemHolder> {
    private static final String TAG = AdapterLinearListFound.class.getSimpleName();

    private static final String REGEX_SPLIT_PARAMETER = ", ";

    private ViewModel mViewModel;
    private List<ListItemCombined> mLocalList;

    public AdapterLinearListSought(@NonNull ViewModel viewModel) {
        mViewModel = viewModel;
        mLocalList = mViewModel.getSoughtListItems();
    }

    public void onUpdated() {
        mLocalList = mViewModel.getSoughtListItems();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new ListItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (mLocalList != null) {
            return mLocalList.size();
        } else {
            return 0;
        }
    }

    class ListItemHolder extends RecyclerView.ViewHolder {
        private String mTextName;
        private String mTextQuantity;
        private String mTextOrigin;
        private String mTextUnit;

        private Context mContext;
        private TableLayout mAmountsTable;
        private TextView mName;
        private TextView mOrigin;

        private int mPaddingVertical;
        private int mPaddingHorizontal;
        private int mTextColorAmounts;
        private float mTextSizeAmounts;

        ListItemHolder(View itemView) {
            super(itemView);

            Resources resources = itemView.getResources();
            mTextName = resources.getString(R.string.list_item_name);
            mTextQuantity = resources.getString(R.string.list_item_quantity);
            mTextOrigin = resources.getString(R.string.list_item_origin);
            mTextUnit = resources.getString(R.string.list_item_unit);

            mPaddingVertical = (int) resources.getDimension(R.dimen.pad_vert_list_inside);
            mPaddingHorizontal = (int) resources.getDimension(R.dimen.pad_horz_list_inside);
            mTextColorAmounts = resources.getColor(R.color.colorItemAmounts);
            mTextSizeAmounts = resources.getDimension(R.dimen.text_size_list_amounts);

            mContext = mViewModel.getApplication().getApplicationContext();

            mName = itemView.findViewById(R.id.tv_item_name);
            mOrigin = itemView.findViewById(R.id.tv_item_origin);
            mAmountsTable = itemView.findViewById(R.id.tl_item_table);
        }

        void bind(int position) {
            ListItemCombined current = mLocalList.get(position);
            mName.setText(String.format(mTextName, current.getName()));
            mOrigin.setText(String.format(mTextOrigin, current.getSourceName()));

            String quantity = current.getQuantity();
            String [] quantities = quantity.split(REGEX_SPLIT_PARAMETER);
            String unit = current.getUnit();
            String [] units = unit.split(REGEX_SPLIT_PARAMETER);
            // https://stackoverflow.com/a/47479404
            // https://stackoverflow.com/a/8907095
            TextView currentTextView;
            TableRow currentTableRow;

            mAmountsTable.removeAllViews();

            currentTableRow = new TableRow(mContext);
            for (int i = 0; i < quantities.length; i++) {
                Log.v(TAG, "quantities[" + i + "]: " + quantities[i]);
                currentTextView = new TextView(mContext);
                currentTextView.setText(String.format(mTextQuantity, quantities[i]));
                currentTextView.setPadding(
                        mPaddingHorizontal,
                        mPaddingVertical,
                        mPaddingHorizontal,
                        mPaddingVertical
                );
                currentTextView.setGravity(Gravity.CENTER);
                currentTextView.setTextSize(mTextSizeAmounts);
                currentTextView.setTextColor(mTextColorAmounts);
                currentTableRow.addView(currentTextView);
            }
            mAmountsTable.addView(currentTableRow);

            currentTableRow = new TableRow(mContext);
            for (int i = 0; i < units.length; i++) {
                Log.v(TAG, "units[" + i + "]: " + units[i]);
                currentTextView = new TextView(mContext);
                currentTextView.setText(String.format(mTextUnit, units[i]));
                currentTextView.setPadding(
                        mPaddingHorizontal,
                        mPaddingVertical,
                        mPaddingHorizontal,
                        mPaddingVertical
                );
                currentTextView.setGravity(Gravity.CENTER);
                currentTextView.setTextSize(mTextSizeAmounts);
                currentTextView.setTextColor(mTextColorAmounts);
                currentTableRow.addView(currentTextView);
            }
            mAmountsTable.addView(currentTableRow);
        }
    }
}
