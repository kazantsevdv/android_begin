package com.example.android_begin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_begin.R;
import com.example.android_begin.room.City;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private List<City> citys = new ArrayList<>();
    private OnItemClickListener itemClickListener;

    public CityAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item_city_selection, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTvCity().setText(citys.get(position).getCityName());

    }

    @Override
    public int getItemCount() {
        return citys.size();
    }

    public void addData(List<City> data) {
        citys.clear();
        citys.addAll(data);
        notifyDataSetChanged();
    }


    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, String cityName);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCity = itemView.findViewById(R.id.tv_city);
            CardView cardView = itemView.findViewById(R.id.card_view);
            cardView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, citys.get(getAdapterPosition()).getCityName());
                }
            });
        }

        public TextView getTvCity() {
            return tvCity;
        }
    }
}
