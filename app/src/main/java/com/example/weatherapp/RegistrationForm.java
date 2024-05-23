package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.weatherapp.databinding.ActivityRegistrationFormBinding;

public class RegistrationForm extends AppCompatActivity {
    ActivityRegistrationFormBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityRegistrationFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                // Get user input
                String name = binding.editTextName.getText().toString();
                String email = binding.editTextEmail.getText().toString();

                // Perform validation if needed
                    if (name.isEmpty() || email.isEmpty()) {
                        Toast.makeText(RegistrationForm.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        return; // Exit the method
                    }

                    // Check if email is valid
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(RegistrationForm.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                        return; // Exit the method
                    }

                // Display a toast message
                Toast.makeText(RegistrationForm.this, "Name: " + name + ", Email: " + email, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegistrationForm.this, MainActivity.class);
                    startActivity(intent);

                }
        });
    }
}