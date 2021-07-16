package com.syte.ai.android_sdk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.syte.ai.android_sdk.data.result.offers.Offer;

import org.json.JSONException;

import java.util.List;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    private List<Offer> mData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView floatPrice;
        public TextView originalPrice;
        public TextView parentSku;
        public TextView merchant;
        public TextView description;
        public TextView offer;
        public ImageView imageView;
        public TextView price;
        public TextView bbCategories;
        public TextView id;
        public TextView floatOriginalPrice;
        public TextView categories;
        public TextView sku;
        public TextView brand;
        public TextView originalDataTitle;

        public ViewHolder(View view) {
            super(view);
            floatPrice = (TextView) view.findViewById(R.id.floatPrice);
            originalPrice = (TextView) view.findViewById(R.id.originalPrice);
            parentSku = (TextView) view.findViewById(R.id.parentSku);
            merchant = (TextView) view.findViewById(R.id.merchant);
            description = (TextView) view.findViewById(R.id.description);
            offer = (TextView) view.findViewById(R.id.offer);
            imageView = (ImageView) view.findViewById(R.id.image);
            price = (TextView) view.findViewById(R.id.price);
            bbCategories = (TextView) view.findViewById(R.id.bbCategories);
            id = (TextView) view.findViewById(R.id.id);
            floatOriginalPrice = (TextView) view.findViewById(R.id.floatOriginalPrice);
            categories = (TextView) view.findViewById(R.id.categories);
            sku = (TextView) view.findViewById(R.id.sku);
            brand = (TextView) view.findViewById(R.id.brand);
            originalDataTitle = (TextView) view.findViewById(R.id.od_title);
        }

    }

    public OffersAdapter(List<Offer> dataSet) {
        mData = dataSet;
    }

    @Override
    public OffersAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.offers_list_item, viewGroup, false);

        return new OffersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OffersAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.floatPrice.setText(mData.get(position).getFloatPrice() + "");
        viewHolder.originalPrice.setText(mData.get(position).getOriginalPrice());
        viewHolder.parentSku.setText(mData.get(position).getParentSku());
        viewHolder.merchant.setText(mData.get(position).getMerchant());
        viewHolder.description.setText(mData.get(position).getDescription());
        viewHolder.offer.setText(mData.get(position).getOffer());
        viewHolder.price.setText(mData.get(position).getPrice());
        if (mData.get(position).getBbCategories() != null)
        viewHolder.bbCategories.setText(mData.get(position).getBbCategories().toString());
        viewHolder.id.setText(mData.get(position).getId());
        viewHolder.floatOriginalPrice.setText(mData.get(position).getFloatOriginalPrice() + "");
        if (mData.get(position).getCategories() != null)
        viewHolder.categories.setText(mData.get(position).getCategories().toString());
        viewHolder.sku.setText(mData.get(position).getSku());
        viewHolder.brand.setText(mData.get(position).getBrand());
        if (mData.get(position).getImageUrl() != null)
        Picasso.get().load(mData.get(position).getImageUrl()).into(viewHolder.imageView);
        try {
            if (mData.get(position).getOriginalData() != null)
            viewHolder.originalDataTitle.setText(mData.get(position).getOriginalData().getString("title"));
        } catch (JSONException e) {}
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}