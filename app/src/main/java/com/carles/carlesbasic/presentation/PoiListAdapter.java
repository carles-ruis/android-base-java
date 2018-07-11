package com.carles.carlesbasic.presentation;

import com.carles.carlesbasic.R;
import com.carles.carlesbasic.domain.model.Poi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PoiListAdapter extends RecyclerView.Adapter<PoiListAdapter.PoiListViewHolder> {

    private List<Poi> poiList = new ArrayList<>();
    private PoiListAdapterListener poiListAdapterListener;

    @Override
    public PoiListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PoiListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
            .item_poi_list, parent, false));
    }

    @Override
    public void onBindViewHolder(PoiListViewHolder holder, int position) {
        holder.onBindView(poiList.get(position));
    }

    @Override
    public int getItemCount() {
        return poiList.size();
    }

    public void setItems(List<Poi> poiList) {
        this.poiList.clear();
        this.poiList.addAll(poiList);
        notifyDataSetChanged();
    }

    class PoiListViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;

        public PoiListViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.poilist_item_title_textview);
            itemView.setOnClickListener(view -> poiListAdapterListener.onPoiClicked(poiList.get(getAdapterPosition())));
        }

        public void onBindView(Poi item) {
            titleTextView.setText(item.title);
        }
    }

    public void setPoiListAdapterListener(PoiListAdapterListener poiListAdapterListener) {
        this.poiListAdapterListener = poiListAdapterListener;
    }

    interface PoiListAdapterListener {
        void onPoiClicked(Poi poi);
    }
}
