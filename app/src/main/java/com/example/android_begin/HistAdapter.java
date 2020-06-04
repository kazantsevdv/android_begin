package com.example.android_begin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistAdapter extends RecyclerView.Adapter<HistAdapter.ViewHolder> {
    private List<WeatherHist> weatherHists;

    public HistAdapter(List<WeatherHist> weatherData) {
        this.weatherHists = weatherData;
    }

    @NonNull
    @Override
    public HistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item_hist, parent, false);
        return new HistAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTvData().setText(weatherHists.get(position).getData());
        String strTemperature = weatherHists.get(position).getTemperature() + "℃";
        holder.getTvTemp().setText(strTemperature);
    }

    @Override
    public int getItemCount() {
        return weatherHists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvData;
        private TextView tvTemp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvData = itemView.findViewById(R.id.tv_data);
            tvTemp = itemView.findViewById(R.id.tv_temp);
        }

        public TextView getTvData() {
            return tvData;
        }

        public TextView getTvTemp() {
            return tvTemp;
        }
    }
}


