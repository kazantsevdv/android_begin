package com.example.android_begin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_begin.R;
import com.example.android_begin.room.WeatherData;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class HistAdapter extends RecyclerView.Adapter<HistAdapter.ViewHolder> {
    private List<WeatherData> weatherHists;


    public HistAdapter(List<WeatherData> weatherHists) {
        this.weatherHists = weatherHists;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item_hist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTvCity().setText(weatherHists.get(position).getCityName());

        long data = weatherHists.get(position).getData();
        Date dt = new Date(data);
        holder.getTvDate().setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(dt));
        holder.getTvtemp().setText(weatherHists.get(position).getTemperature());
    }

    @Override
    public int getItemCount() {
        return weatherHists.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCity;
        private TextView tvDate;
        private TextView tvtemp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCity = itemView.findViewById(R.id.tv_city);
            tvDate = itemView.findViewById(R.id.tv_data);
            tvtemp = itemView.findViewById(R.id.tv_temperature);
        }

        public TextView getTvCity() {
            return tvCity;
        }

        public TextView getTvDate() {
            return tvDate;
        }

        public TextView getTvtemp() {
            return tvtemp;
        }

    }
}
