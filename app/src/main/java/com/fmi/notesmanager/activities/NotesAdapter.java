package com.fmi.notesmanager.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fmi.notesmanager.R;
import com.fmi.notesmanager.model.Note;

import java.util.List;

public class NotesAdapter extends ArrayAdapter<Note> {

    private final Context context;
    private final List<Note> values;

    public NotesAdapter(Context context, int resource, List<Note> objects) {
        super(context, resource, objects);
        this.context = context;
        this.values = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Note note = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_list_item, parent, false);
        }
        TextView tvTitle = convertView.findViewById(R.id.tvNote);
        if (note != null) {
            tvTitle.setText(note.getTitle() != null ? note.getTitle() : "No title");
        }

        return convertView;
    }
}
