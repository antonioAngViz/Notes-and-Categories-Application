package com.example.notesandcategoriesapplication.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;




@Entity(tableName = "notes",
    foreignKeys = @ForeignKey(
        entity = Category.class,       // clase de la categoria
        parentColumns = "category_id", // id de la llave en la tabla
        childColumns = "category_id",  // nombre donde se guarda la id para notas
        onDelete = ForeignKey.CASCADE  // si se llega a borrar categoría tambien se borrara las notas
    ),
        // buscar notas por categoría mas rápido
        indices = {@Index("category_id")}
)
public class Note {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    public int noteId;

    @ColumnInfo(name = "note_title")
    public String noteTitle;

    @ColumnInfo(name = "note_content")
    public String noteContent;

    @ColumnInfo(name = "created_at")
    public String createdAt;


    @ColumnInfo(name = "category_id")
    public int categoryId;



}




