package com.example.myapplication;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextEmployeeNumber;
    private Button buttonSave;
    private TextView textViewEmployeeNumbers;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmployeeNumber = findViewById(R.id.editTextEmployeeNumber);
        buttonSave = findViewById(R.id.buttonSave);
        textViewEmployeeNumbers= findViewById(R.id.textViewEmployeeNumbers);
        dbHelper = new DatabaseHelper(this);
        loadEmployeeNumbers();


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEmployeeNumber();
            }
        });

    }

    private void saveEmployeeNumber() {
        String employeeNumber = editTextEmployeeNumber.getText().toString().trim();
        if (!employeeNumber.isEmpty()) {

            long rowId = dbHelper.insertEmployeeNumber(employeeNumber);
            if (rowId > 0) {
                Toast.makeText(this, "Employee Number saved", Toast.LENGTH_SHORT).show();
                editTextEmployeeNumber.setText("");
                loadEmployeeNumbers();
                animateButton(); // Animate button
            } else {
                Toast.makeText(this, "Unable to save number", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Enter employee number", Toast.LENGTH_SHORT).show();
        }
    }

    private  void loadEmployeeNumbers(){
        List<String> employeeNumberList = dbHelper.getAllEmployeeNumbers();
        StringBuilder stringBuilder = new StringBuilder();
        if (!employeeNumberList.isEmpty()) {
            for (String number : employeeNumberList) {
                stringBuilder.append(number).append("\n");
            }
            animateTextView(stringBuilder.toString());
        }
        else {
            textViewEmployeeNumbers.setText(""); // Set to empty string if the list is empty
        }
    }

    private void animateButton() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(buttonSave, "scaleX", 1f, 1.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(buttonSave, "scaleY", 1f, 1.2f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(250); // Set duration
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator()); // Set easing
        animatorSet.start();
    }


    private void animateTextView(String text) {
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(textViewEmployeeNumbers, "alpha", 0f, 1f);
        fadeIn.setDuration(500); // Animation duration
        fadeIn.setInterpolator(new AccelerateDecelerateInterpolator());
        textViewEmployeeNumbers.setText(text);
        fadeIn.start();

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(textViewEmployeeNumbers, "scaleX", 0.9f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(textViewEmployeeNumbers, "scaleY", 0.9f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(250); // Set duration
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();

    }

}
/* with out animation
public class MainActivity extends AppCompatActivity {

    private EditText editTextEmployeeNumber;
    private Button buttonSave;
    private TextView textViewEmployeeNumbers;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmployeeNumber = findViewById(R.id.editTextEmployeeNumber);
        buttonSave = findViewById(R.id.buttonSave);
        textViewEmployeeNumbers= findViewById(R.id.textViewEmployeeNumbers);
        dbHelper = new DatabaseHelper(this);
        loadEmployeeNumbers();


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEmployeeNumber();
            }
        });

    }

    private void saveEmployeeNumber() {
        String employeeNumber = editTextEmployeeNumber.getText().toString().trim();
        if (!employeeNumber.isEmpty()) {

            long rowId= dbHelper.insertEmployeeNumber(employeeNumber);
            if(rowId>0){
                Toast.makeText(this, "Employee Number saved", Toast.LENGTH_SHORT).show();
                editTextEmployeeNumber.setText("");
                loadEmployeeNumbers();
            } else{
                Toast.makeText(this, "Unable to save number", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Enter employee number", Toast.LENGTH_SHORT).show();
        }
    }

    private  void loadEmployeeNumbers(){
        List<String> employeeNumberList = dbHelper.getAllEmployeeNumbers();
        StringBuilder stringBuilder = new StringBuilder();
        if (!employeeNumberList.isEmpty()) {
            for (String number : employeeNumberList) {
                stringBuilder.append(number).append("\n");
            }
            textViewEmployeeNumbers.setText(stringBuilder.toString());
        }
    }
}*/