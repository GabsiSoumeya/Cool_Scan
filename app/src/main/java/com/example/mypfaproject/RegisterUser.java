package com.example.mypfaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterUser extends AppCompatActivity {
    private EditText emailTextView;
    private TextInputLayout passwordTextView;
    private Button Btn;
    //private ProgressBar progressbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // initialising all views through id defined above
        emailTextView = findViewById(R.id.email_register);
        passwordTextView = findViewById(R.id.Pwd);
        Btn = findViewById(R.id.Signup);

        // Set on Click Listener on Registration button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });

    }

        private void registerNewUser() {

            // show the visibility of progress bar to show loading
            //progressbar.setVisibility(View.VISIBLE);

            // Take the value of two edit texts in Strings
            String email, password;
            email = emailTextView.getText().toString();
            password = passwordTextView.getEditText().getText().toString();

            // Validations for input email and password
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(),
                        "Please enter email!!",
                        Toast.LENGTH_LONG)
                        .show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(),
                        "Please enter password!!",
                        Toast.LENGTH_LONG)
                        .show();
                return;
            }

// create new user or register new user
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),
                                        "Registration successful!",
                                        Toast.LENGTH_LONG)
                                        .show();

                                // hide the progress bar
                               // progressBar.setVisibility(View.GONE);

                                // if the user created intent to login activity
                                Intent intent
                                        = new Intent(RegisterUser.this,
                                        MainActivity.class);
                                startActivity(intent);
                            }
                            else {

                                // Registration failed
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Registration failed!!"
                                                + " Please try again later",
                                        Toast.LENGTH_LONG)
                                        .show();

                                // hide the progress bar
                               // progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }
}