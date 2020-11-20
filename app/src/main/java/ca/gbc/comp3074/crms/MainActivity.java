package ca.gbc.comp3074.crms;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import ca.gbc.comp3074.crms.models.User;

public class MainActivity extends AppCompatActivity {

    private TextView loginActivityLabel;
    private EditText fullNameInput, emailInput, passwordInput;
    private Button registerButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loginActivityLabel = findViewById(R.id.loginActivityLabel);
        loginActivityLabel.setOnClickListener(v -> {
            Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginIntent);
        });

        fullNameInput = findViewById(R.id.fullNameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);

        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> registerUser());
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            startHomeActivity(currentUser);
        }
    }

    private void registerUser() {
        String email = emailInput.getText().toString();
        String fullName = fullNameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(fullName.isEmpty()){
            fullNameInput.setError("Full Name is required");
            fullNameInput.requestFocus();
            return;
        }

        if(email.isEmpty()){
            emailInput.setError("Email is required");
            emailInput.requestFocus();
            return;
        }

        if(password.isEmpty()){
            passwordInput.setError("Password is required");
            passwordInput.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(MainActivity.this, "User has been registered", Toast.LENGTH_LONG).show();
                        FirebaseUser user = mAuth.getCurrentUser();

                        User newUser = new User(fullName, email);
                        Toast.makeText(MainActivity.this, "CREATED NEW USER " + mAuth.getCurrentUser().getUid(), Toast.LENGTH_LONG).show();
                        db.collection("users")
                                .document(user.getUid())
                                .set(newUser)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(MainActivity.this, "User has been added to Firestore", Toast.LENGTH_LONG).show();
                                    startHomeActivity(user);
                                })
                                .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "User failed to be added to Firestore", Toast.LENGTH_LONG).show());
                    }else{
                        Toast.makeText(MainActivity.this, "User failed to register", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void startHomeActivity(FirebaseUser user){
        Intent intent = new Intent(getApplicationContext(), MakeReservation.class);
        intent.putExtra("currentUser", user);
        startActivity(intent);
    }
}






