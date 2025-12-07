package com.example.notesandcategoriesapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.notesandcategoriesapplication.controller.NoteController;


public class AddCategoryActivity extends AppCompatActivity {


    private EditText etCategoryName;
    private Button btnSaveCategory;

    private NoteController noteController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        initViews();

        noteController = new NoteController(this);


        //  boton guardar
        btnSaveCategory.setOnClickListener(view -> {
            String name = etCategoryName.getText().toString().trim();

            // esta vacío?
            if (name.isEmpty()) {
                etCategoryName.setError("El nombre es obligatorio");
                return;
            }

            saveCategory(name);
        });




    }


    private void saveCategory(String name) {
        // metodo del controladoe
        boolean success = noteController.addCategory(name);

        if (success) {
            Toast.makeText(this, R.string.categoria_guardada_ok, Toast.LENGTH_SHORT).show();
            finish(); // cerramos pantalla
        } else {
            Toast.makeText(this, R.string.error_guardar, Toast.LENGTH_SHORT).show();
        }
    }


    private void initViews() {
        etCategoryName = findViewById(R.id.etCategoryName);
        btnSaveCategory = findViewById(R.id.btnSaveCategory);
    }

}




