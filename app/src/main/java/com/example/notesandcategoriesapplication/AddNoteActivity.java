package com.example.notesandcategoriesapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.notesandcategoriesapplication.controller.NoteController;
import com.example.notesandcategoriesapplication.model.Category;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity {


    private EditText etNoteTitle, etNoteContent;
    private Spinner spCategory;
    private Button btnSaveNote;
    private NoteController noteController;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();

        noteController = new NoteController(this);


        // cargamos el spiner de las categorias que estan en la base de datos
        cargarSpinner();

        // 2. CONFIGURAR BOTÓN GUARDAR
        btnSaveNote.setOnClickListener(view -> {
            saveNote();
        });





    }


    private void initViews() {
        etNoteTitle = findViewById(R.id.etNoteTitle);
        etNoteContent = findViewById(R.id.etNoteContent);
        spCategory = findViewById(R.id.spCategory);
        btnSaveNote = findViewById(R.id.btnSaveNote);
    }



    private void cargarSpinner() {
        // lista de las categorias
        List<Category> categories = noteController.getAllCategories();

        // esta vacia?
        if (categories.isEmpty()) {
            Toast.makeText(this, R.string.create_category, Toast.LENGTH_LONG).show();
            finish(); //regresamos a la pantalla de atras
            return;
        }

        //  adaptador del Spinner
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // mostarr el spinner
        spCategory.setAdapter(adapter);
    }




    private void saveNote() {
        String title = etNoteTitle.getText().toString().trim();
        String content = etNoteContent.getText().toString().trim();

        // esta vacio el titulo?
        if (title.isEmpty()) {
            etNoteTitle.setError(getString(R.string.title_obligatorio));
            return;
        }

        // esta vacio el contenido?
        if (content.isEmpty()) {
            etNoteContent.setError(getString(R.string.content_obligatorio));
            return;
        }


        // se obntiene la categoria  que se selecciona
        Category selectedCategory = (Category) spCategory.getSelectedItem();

        // selecciono una categoria?
        if (selectedCategory == null) {
            Toast.makeText(this, R.string.selecciona_categoria, Toast.LENGTH_SHORT).show();
            return;
        }

        // Generar fecha
        String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());


        // guardar
        boolean success = noteController.addNote(title, content, currentDate, selectedCategory.categoryId);

        if (success) {
            Toast.makeText(this, R.string.guardado_correctamente, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, R.string.error_guardar_nota, Toast.LENGTH_SHORT).show();
        }
    }


}


