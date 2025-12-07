package com.example.notesandcategoriesapplication.view; // Tu paquete

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesandcategoriesapplication.R;
import com.example.notesandcategoriesapplication.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {


    private final List<Note> noteList = new ArrayList<>();

    // metodo para actualizar la lista cuando se use eñ filtro o bsuque
    public void setData(List<Note> notes){
        noteList.clear();
        if(notes != null){
            noteList.addAll(notes);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);

        // mostrar los datos de la Nota en la vista
        holder.txtTitle.setText(note.noteTitle);
        holder.txtContent.setText(note.noteContent);
        holder.txtDate.setText(note.createdAt);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle, txtContent, txtDate;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.tvNoteTitle);
            txtContent = itemView.findViewById(R.id.tvNoteContent);
            txtDate = itemView.findViewById(R.id.tvNoteDate);
        }
    }
}