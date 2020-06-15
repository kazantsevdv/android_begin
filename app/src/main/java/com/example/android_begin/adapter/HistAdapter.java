package com.example.android_begin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_begin.R;
import com.example.android_begin.WeatherHist;

import java.util.ArrayList;
import java.util.List;

public class HistAdapter extends RecyclerView.Adapter<HistAdapter.ViewHolder> implements Filterable {
    private List<WeatherHist> weatherHists;
    private List<WeatherHist> weatherHistsFiltered;

    public HistAdapter(List<WeatherHist> weatherHists) {
        this.weatherHists = weatherHists;
        weatherHistsFiltered = weatherHists;
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
        holder.getTvCity().setText(weatherHistsFiltered.get(position).getCityName());
        holder.getTvDate().setText(weatherHistsFiltered.get(position).getDate().toString());
        String strTemperature = weatherHistsFiltered.get(position).getTemp() + " ℃";
        holder.getTvtemp().setText(strTemperature);
    }

    @Override
    public int getItemCount() {
        return weatherHistsFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                List<WeatherHist> HistsFiltered = new ArrayList<>();

                if (charString.isEmpty()) {
                    HistsFiltered = weatherHists;
                } else {
                    for (int i = 0; i < weatherHists.size(); i++) {
                        if (weatherHists.get(i).getCityName().toLowerCase().contains(charString.toLowerCase())) {
                            HistsFiltered.add(weatherHists.get(i));
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = HistsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                weatherHistsFiltered = (List<WeatherHist>) results.values;
                notifyDataSetChanged();
            }
        };
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
