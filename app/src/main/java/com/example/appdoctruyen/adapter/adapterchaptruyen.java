package com.example.appdoctruyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.object.Chapter;

import java.util.List;

public class adapterchaptruyen extends ArrayAdapter<Chapter> {
    private final Context context;
    private final List<Chapter> chapters;

    public adapterchaptruyen(@NonNull Context context, int resource, @NonNull List<Chapter> objects) {
        super(context, resource, objects);
        this.context = context;
        this.chapters = objects;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_chapter, parent, false);
        }

        TextView txtIDChapter = convertView.findViewById(R.id.txtIDChapter);
        TextView txtTenChapter = convertView.findViewById(R.id.txtTenChapter);

        Chapter chapter = getItem(position);

        if (chapter != null) {
            txtIDChapter.setText(String.valueOf(chapter.getId()));
            txtTenChapter.setText(chapter.getName());
        }

        return convertView;
    }
}
