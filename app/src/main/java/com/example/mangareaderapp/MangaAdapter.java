package com.example.mangareaderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class MangaAdapter extends ArrayAdapter<Manga> {

    ArrayList<Manga> mangas;

    public MangaAdapter(Context context, int textViewResourceId, ArrayList<Manga> objects) {
        super(context, textViewResourceId, objects);
        mangas = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.custom_list_view_items, null);

        TextView textView = (TextView) v.findViewById(R.id.textView);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        textView.setText(mangas.get(position).getTitle());

        // TODO: Don't just blindly take the first cover in the list
        MangaCover cover = mangas.get(position).getCovers().get(0);
        byte [] coverBytes = cover.getCoverBytes(256);
        //System.out.println("cover for " + mangas.get(position).getTitle() + " is " + coverBytes.length + " bytes long");
        //System.out.println("cover bytes: " + String.format("%02X%02X%02X", coverBytes[0], coverBytes[1], coverBytes[2]));
        //System.out.println("Cover details: " + cover);
        Bitmap bitmap = BitmapFactory.decodeByteArray(coverBytes, 0, coverBytes.length);
        imageView.setImageBitmap(bitmap);
        return v;

    }

}


