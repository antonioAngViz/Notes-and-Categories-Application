package com.example.notesandcategoriesapplication.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface NoteDao {

    @Insert
    void insertNote(Note notes);

    @Query("SELECT * FROM notes")
    List<Note> getAllNotes();


    @Query("SELECT * FROM notes WHERE category_id = :id")
    List<Note> getNotesByCategory(int id);

    @Query("SELECT * FROM notes WHERE note_title LIKE '%' || :search || '%'")
    List<Note> searchNotes(String search);

}
