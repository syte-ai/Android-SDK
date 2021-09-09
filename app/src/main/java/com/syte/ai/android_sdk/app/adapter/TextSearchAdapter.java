package com.syte.ai.android_sdk.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.syte.ai.android_sdk.app.R;
import com.syte.ai.android_sdk.data.result.text_search.TextSearchResult;

public class TextSearchAdapter extends RecyclerView.Adapter<TextSearchAdapter.ViewHolder> {

    private TextSearchResult mData;

    public TextSearchAdapter(TextSearchResult result) {
        mData = result;
    }

    public void setData(TextSearchResult data) {
        mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_search_list_item, parent, false);
        return new TextSearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text.setText(mData.getResult().getHits().get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.getResult().getHits().size();
        } else return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        public ViewHolder(View view) {
            super(view);
            text = view.findViewById(R.id.suggested_text);
        }

    }

}
