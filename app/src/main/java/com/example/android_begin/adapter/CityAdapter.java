package com.example.android_begin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_begin.R;
import com.example.android_begin.WeatherData;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private List<WeatherData> weatherData;
    private OnItemClickListener itemClickListener;

    public CityAdapter(List<WeatherData> weatherData) {
        this.weatherData = weatherData;
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
        holder.getTvCity().setText(weatherData.get(position).getCityName());
        String strTemperature = weatherData.get(position).getTemperature() + "℃";
        holder.getTvTemp().setText(strTemperature);
    }

    @Override
    public int getItemCount() {
        return weatherData.size();
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCity;
        private TextView tvTemp;
        private CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCity = itemView.findViewById(R.id.tv_city);
            tvTemp = itemView.findViewById(R.id.tv_temp);
            cardView = itemView.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }

        public TextView getTvTemp() {
            return tvTemp;
        }

        public TextView getTvCity() {
            return tvCity;
        }
    }
}
