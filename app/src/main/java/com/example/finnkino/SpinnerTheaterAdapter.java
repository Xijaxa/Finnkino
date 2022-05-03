package com.example.finnkino;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finnkino.Classes.MovieTheater;

import java.util.ArrayList;

public class SpinnerTheaterAdapter extends ArrayAdapter<MovieTheater> {

    LayoutInflater layoutInflater;

    public SpinnerTheaterAdapter(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = layoutInflater.inflate(R.layout.custom_spinner_adapter, null,
                true);
        MovieTheater theater = getItem(position);
        TextView textView = rowView.findViewById(R.id.movieInfoTextView);
        textView.setText(theater.getLocation());
        return rowView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_spinner_adapter, parent,
                    false);
        }
        MovieTheater theater = getItem(position);
        TextView textView = convertView.findViewById(R.id.movieInfoTextView);
        textView.setText(theater.getLocation());

        return convertView;
    }
}