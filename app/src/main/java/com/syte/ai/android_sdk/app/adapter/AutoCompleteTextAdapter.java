package com.syte.ai.android_sdk.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.syte.ai.android_sdk.app.R;
import com.syte.ai.android_sdk.data.result.auto_complete.AutoCompleteResult;

public class AutoCompleteTextAdapter extends RecyclerView.Adapter<AutoCompleteTextAdapter.ViewHolder> {

    private AutoCompleteResult mData;

    public AutoCompleteTextAdapter(AutoCompleteResult result) {
        mData = result;
    }

    public void setData(AutoCompleteResult data) {
        mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.auto_complete_list_item, parent, false);
        return new AutoCompleteTextAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text.setText(mData.getResults().getSuggestedSearchTerms().get(position).getSearchTerm());
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.getResults().getSuggestedSearchTerms().size();
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
