package com.example.notesandcategoriesapplication.controller;

import android.content.Context;
import android.util.Log;

import com.example.notesandcategoriesapplication.model.Category;
import com.example.notesandcategoriesapplication.model.CategoryDao;
import com.example.notesandcategoriesapplication.model.Note;
import com.example.notesandcategoriesapplication.model.NoteDao;
import com.example.notesandcategoriesapplication.model.NotesDatabase;

import java.util.List;



public class NoteController {
    private final CategoryDao categoryDao;
    private final NoteDao noteDao;


    public NoteController(Context context) {
        NotesDatabase database = NotesDatabase.getInstance(context);
        categoryDao = database.categoryDao();
        noteDao = database.noteDao();
    }

    //Create a category
    public boolean addCategory(String categoryName){
        try{
            Category category = new Category();
            category.categoryName = categoryName;

            categoryDao.insertCategory(category);
            Log.i("CATEGORY_SAVE","La categoria se almacenó correctamente");
            return true;

        }catch (Exception e){
            Log.e("CATEGORY_ERROR",e.getMessage());
            return false;
        }

    }

    //Get all categories
    public List<Category> getAllCategories(){
        return categoryDao.getAllCategories();
    }



    //Create a category
    public boolean addNote(String noteTitle, String noteContent, String createdAt, int categoryId){
        try{
            Note note = new Note();
            note.noteTitle = noteTitle;
            note.noteContent = noteContent;
            note.createdAt = createdAt;
            note.categoryId = categoryId;

            noteDao.insertNote(note);
            Log.i("NOTE_SAVE","La nota se almacenó correctamente");
            return true;

        }catch (Exception e){
            Log.e("NOTE_ERROR",e.getMessage());
            return false;
        }

    }

    //Get all categories
    public List<Note> getAllNotes(){
        return noteDao.getAllNotes();
    }

    // obtener notas por categoria
    public List<Note> getNotesByCategory(int categoryId){
        return noteDao.getNotesByCategory(categoryId);
    }

    // buscar notas por texto
    public List<Note> searchNotes(String search){
        return noteDao.searchNotes(search);
    }


}
