package com.example.finnkino;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finnkino.Classes.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


// Custom adapter for spinner that shows movie names with pictures
public class SpinnerMovieAdapter extends ArrayAdapter<Movie> {

    LayoutInflater layoutInflater;
    public SpinnerMovieAdapter(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = layoutInflater.inflate(R.layout.custom_spinner_adapter, null,
                true);
        Movie movie = getItem(position);
        TextView textView = rowView.findViewById(R.id.movieInfoTextView);
        ImageView imageView = rowView.findViewById(R.id.imageView);
        textView.setText(movie.getName());
        if(movie.getName().equals("Valitse elokuva")) {
        } else {
            Picasso.get().load(movie.getImage()).into(imageView);
        }
        return rowView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_spinner_adapter, parent,
                    false);
        }
        Movie movie = getItem(position);
        TextView textView = convertView.findViewById(R.id.movieInfoTextView);
        ImageView imageView = convertView.findViewById(R.id.imageView);
        textView.setText(movie.getName());
        if(movie.getName().equals("Valitse elokuva")) {
        } else {
            Picasso.get().load(movie.getImage()).into(imageView);
        }
        return convertView;
    }




}
