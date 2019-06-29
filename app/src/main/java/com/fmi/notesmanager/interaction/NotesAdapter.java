package com.fmi.notesmanager.interaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fmi.notesmanager.R;
import com.fmi.notesmanager.model.Note;

import java.util.List;

/**
 * The notes adapter used to populate the {@link android.widget.ListView}
 * after the notes are retrieved from the database.
 *
 * @author angel.beshirov
 */
public class NotesAdapter extends ArrayAdapter<Note> {

    private static final String NO_TITLE = "No title";

    public NotesAdapter(Context context, int resource, List<Note> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Note note = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_list_item, parent, false);
        }
        TextView tvTitle = convertView.findViewById(R.id.tvNote);
        if (note != null) {
            tvTitle.setText(note.getTitle() != null ? note.getTitle() : NO_TITLE);
        }

        return convertView;
    }
}
