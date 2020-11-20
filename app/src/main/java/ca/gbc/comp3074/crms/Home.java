package ca.gbc.comp3074.crms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {

    Button logoutBtn;
    TextView currentUserText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logoutBtn = findViewById(R.id.logoutButton);
        currentUserText = findViewById(R.id.currentUserText);

        Intent intent = getIntent();
        FirebaseUser currentUser = (FirebaseUser) intent.getExtras().get("currentUser");

//        currentUserText.setText("USER: " + currentUser.getEmail() + ", " + currentUser.getUid());
        currentUserText.setText("USER: " + currentUser.getEmail() );

        logoutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent1 = new Intent(Home.this, LoginActivity.class);
            startActivity(intent1);
        });
    }


}