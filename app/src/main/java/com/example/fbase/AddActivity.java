package com.example.fbase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    EditText addName, addCourse, addEmail, addImg;
    Button saveButton, backButton;

    private DatabaseReference dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        dataBase = FirebaseDatabase.getInstance().getReference();

        this.addName = findViewById(R.id.addName);
        this.addCourse = findViewById(R.id.addCourse);
        this.addEmail = findViewById(R.id.addEmail);
        this.addImg = findViewById(R.id.addImg);

        setSaveButton();
        setBackButton();
    }

    private void setSaveButton(){
        this.saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addData();
                finish();
            }
        });
    }

    private void setBackButton(){
        this.backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addData(){

        String newName = addName.getText().toString();
        String newCourse = addCourse.getText().toString();
        String newEmail = addEmail.getText().toString();
        String newImg = addImg.getText().toString();

        Student newStudent = new Student(newName, newCourse, newEmail, newImg);

        dataBase.child(Student.db).push().setValue(newStudent).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Data Inserted Successfully.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to insert.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}