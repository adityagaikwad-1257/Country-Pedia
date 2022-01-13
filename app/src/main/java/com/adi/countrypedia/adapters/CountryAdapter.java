package com.adi.countrypedia.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.adi.countrypedia.R;
import com.adi.countrypedia.db.Converters;
import com.adi.countrypedia.models.Country;
import com.squareup.picasso.Picasso;


public class CountryAdapter extends ListAdapter<Country, CountryAdapter.CountryViewHolder> {
    Dialog dialog;

    public CountryAdapter(Context context) {
        super(DIFF_CALLBACK);
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.split_view);
    }

    public static final DiffUtil.ItemCallback<Country> DIFF_CALLBACK = new DiffUtil.ItemCallback<Country>() {
        @Override
        public boolean areItemsTheSame(@NonNull Country oldItem, @NonNull Country newItem) {
            return oldItem.getName().getCommon().matches(newItem.getName().getCommon());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Country oldItem, @NonNull Country newItem) {
            return Converters.getFromHash(oldItem.getFlags()).matches(Converters.getFromHash(newItem.getFlags()))
                    && Converters.getCapital(oldItem.getCapital()).matches(Converters.getCapital(newItem.getCapital()))
                    && Converters.getCapital(oldItem.getBorders()).matches(Converters.getCapital(newItem.getBorders()))
                    && Converters.getFromHash(oldItem.getLanguages()).matches(Converters.getFromHash(newItem.getLanguages()))
                    && oldItem.getRegion().matches(newItem.getRegion())
                    && oldItem.getSubregion().matches(newItem.getSubregion())
                    && oldItem.getPopulation() == newItem.getPopulation();
        }
    };

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.collapse_view,
                parent,
                false
        );
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country country = getItem(position);

        holder.nameTv.setText(country.getName().getCommon());
        String flagUrl = "";
        if (country.getFlags().containsKey("png"))
            flagUrl = country.getFlags().get("png");
        else if (country.getFlags().containsKey("svg"))
            flagUrl = country.getFlags().get("svg");

        Picasso.get()
                .load(flagUrl)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.flagImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetailedDialog(country);
            }
        });
    }

    private void showDetailedDialog(Country country){
        ImageView flagImage = dialog.findViewById(R.id.flag_image_view_split);
        TextView name = dialog.findViewById(R.id.country_name_split);
        TextView capital = dialog.findViewById(R.id.capital_split_view);
        TextView languages = dialog.findViewById(R.id.languages_split_view);
        TextView borders = dialog.findViewById(R.id.borders_split_view);
        TextView region = dialog.findViewById(R.id.region_split_view);
        TextView subRegion = dialog.findViewById(R.id.sub_region_split_view);
        TextView population = dialog.findViewById(R.id.population_split_view);

        String flagUrl = "";
        if (country.getFlags().containsKey("png"))
            flagUrl = country.getFlags().get("png");
        else if (country.getFlags().containsKey("svg"))
            flagUrl = country.getFlags().get("svg");

        Picasso.get()
                .load(flagUrl)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(flagImage);

        name.setText(country.getName().getCommon());
        capital.setText(Converters.getCapital(country.getCapital()));
        languages.setText(Converters.getFromHash(country.getLanguages()));
        borders.setText(Converters.getCapital(country.getBorders()));
        region.setText(country.getRegion());
        subRegion.setText(country.getSubregion());
        population.setText(Converters.getPopulation(country.getPopulation()));

        dialog.show();
    }

    protected static class CountryViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv;
        ImageView flagImage;
        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            flagImage = itemView.findViewById(R.id.flag_image_view_collapse);
            nameTv = itemView.findViewById(R.id.country_name_collapse);
        }
    }

}
