package com.example.notesandcategoriesapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesandcategoriesapplication.controller.NoteController;
import com.example.notesandcategoriesapplication.model.Category;
import com.example.notesandcategoriesapplication.model.Note;
import com.example.notesandcategoriesapplication.view.NoteAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteController noteController;
    private NoteAdapter noteAdapter;



    private EditText etSearch;
    private Spinner spFilterCategory;
    private RecyclerView rvNotes;


    // menu flotante
    private boolean isFabOpen = false;
    private FloatingActionButton fabMain, fabNote, fabCategory;
    private TextView labelNote, labelCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        noteController = new NoteController(this);


        initViews();
        setupRecyclerView();
        setupFabMenu(); // boton flotantes

        // buscador
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // se recarga los resultados mientras escribimos
                loadNotes();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        // spinner
        spFilterCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // se carga la categoria seleccionada
                loadNotes();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // cargar spinner
        recargarSpinner();
        // cargar notas
        loadNotes();
    }

    private void recargarSpinner() {
        List<Category> categories = noteController.getAllCategories();


        Category allCategory = new Category();
        allCategory.categoryId = -1;
        allCategory.categoryName = "[ TODAS ]";
        categories.add(0, allCategory);

        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFilterCategory.setAdapter(adapter);
    }

    private void loadNotes() {
        String query = etSearch.getText().toString().trim();
        Category selectedCategory = (Category) spFilterCategory.getSelectedItem();

        List<Note> notes;

        // filtar
        if (!query.isEmpty()) {
            // texto
            notes = noteController.searchNotes(query);
        } else if (selectedCategory != null && selectedCategory.categoryId != -1) {
            //categoria
            notes = noteController.getNotesByCategory(selectedCategory.categoryId);
        } else {
            //todas
            notes = noteController.getAllNotes();

        }

        noteAdapter.setData(notes);
    }

    private void setupRecyclerView() {
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter();
        rvNotes.setAdapter(noteAdapter);
    }

    // menu flotante cuando es que se agregara una nota o categoria
    private void setupFabMenu() {
        fabMain.setOnClickListener(view -> {
            if (isFabOpen) closeFabMenu(); else openFabMenu();
        });

        fabNote.setOnClickListener(view -> {
            closeFabMenu();
            startActivity(new Intent(MainActivity.this, AddNoteActivity.class));
        });

        fabCategory.setOnClickListener(view -> {
            closeFabMenu();
            startActivity(new Intent(MainActivity.this, AddCategoryActivity.class));
        });
    }

    private void openFabMenu() {
        isFabOpen = true;
        fabNote.setVisibility(View.VISIBLE);
        fabCategory.setVisibility(View.VISIBLE);
        labelNote.setVisibility(View.VISIBLE);
        labelCategory.setVisibility(View.VISIBLE);
        fabMain.animate().rotation(45f).setDuration(300).start();
    }

    private void closeFabMenu() {
        isFabOpen = false;
        fabNote.setVisibility(View.GONE);
        fabCategory.setVisibility(View.GONE);
        labelNote.setVisibility(View.GONE);
        labelCategory.setVisibility(View.GONE);
        fabMain.animate().rotation(0f).setDuration(300).start();
    }

    private void initViews() {
        etSearch = findViewById(R.id.etSearch);
        spFilterCategory = findViewById(R.id.spFilterCategory);
        rvNotes = findViewById(R.id.rvNotes);

        fabMain = findViewById(R.id.fabMain);
        fabNote = findViewById(R.id.fabAddNote);
        fabCategory = findViewById(R.id.fabAddCategory);
        labelNote = findViewById(R.id.tvLabelNote);
        labelCategory = findViewById(R.id.tvLabelCategory);
    }
}