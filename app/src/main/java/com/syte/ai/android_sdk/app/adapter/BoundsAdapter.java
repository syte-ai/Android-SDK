package com.syte.ai.android_sdk.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.syte.ai.android_sdk.app.R;
import com.syte.ai.android_sdk.data.result.offers.Bound;

import java.util.List;

public class BoundsAdapter extends RecyclerView.Adapter<BoundsAdapter.ViewHolder> {

    private List<Bound> mData;
    private ClickListener mListener;

    public interface ClickListener {
        void onClick(Bound bound);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView offerUrl;
        public TextView gender;
        public TextView catalog;
        public TextView label;
        public TextView b0;
        public TextView b1;
        public LinearLayout root;


        public ViewHolder(View view) {
            super(view);
            root = (LinearLayout) view.findViewById(R.id.root);
            offerUrl = (TextView) view.findViewById(R.id.offers_url);
            gender = (TextView) view.findViewById(R.id.gender);
            catalog = (TextView) view.findViewById(R.id.catalog);
            label = (TextView) view.findViewById(R.id.label);
            b0 = (TextView) view.findViewById(R.id.b0);
            b1 = (TextView) view.findViewById(R.id.b1);
        }

    }

    public void setListener(ClickListener listener) {
        mListener = listener;
    }

    public BoundsAdapter(List<Bound> dataSet) {
        mData = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bounds_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(mData.get(position));
                }
            }
        });
        viewHolder.offerUrl.setText(mData.get(position).getItemUrl());
        viewHolder.gender.setText(mData.get(position).getGender());
        viewHolder.catalog.setText(mData.get(position).getCatalog());
        viewHolder.label.setText(mData.get(position).getLabel());
        viewHolder.b0.setText("" + mData.get(position).getB0().get(0) + ", " + mData.get(position).getB0().get(1));
        viewHolder.b1.setText("" + mData.get(position).getB1().get(0) + ", " + mData.get(position).getB1().get(1));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
