package ca.gbc.comp3074.crms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextView registerActivityLabel;
    EditText emailInput, passwordInput;
    Button loginBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        registerActivityLabel = findViewById(R.id.registerActivityLabel);

        emailInput = findViewById(R.id.loginEmailInput);
        passwordInput = findViewById(R.id.loginPasswordInput);

        loginBtn = findViewById(R.id.loginButton);

        registerActivityLabel.setOnClickListener(v -> {
            Intent registerIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(registerIntent);
        });

        loginBtn.setOnClickListener(v -> signInUser());
    }

    private void signInUser() {

        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

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

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        startHomeActivity(user);
                    }else{
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            startHomeActivity(currentUser);
        }
    }

    private void startHomeActivity(FirebaseUser user){
        Intent intent = new Intent(getApplicationContext(), MakeReservation.class);
        intent.putExtra("currentUser", user);
        startActivity(intent);
    }
}