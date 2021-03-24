package com.example.earthquakefinderandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(@NonNull Context context,  @NonNull List<Earthquake> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;
        if(v==null){
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item,parent,false);
        }

       Earthquake e = getItem(position);

        TextView mag = (TextView) v.findViewById(R.id.earthquake_mag);
        TextView loc = (TextView) v.findViewById(R.id.earthquake_loc);
        TextView e_date = (TextView) v.findViewById(R.id.earthquake_date);

        mag.setText(e.getmMag());
        loc.setText(e.getmLoacation());
        e_date.setText(e.getMdate());


        return v;
    }
}
