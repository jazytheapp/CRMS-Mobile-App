package ca.gbc.comp3074.crms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {

    Button logoutBtn;
    TextView currentUserText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.viewProfile:
                startViewProfile();
                return true;
            case R.id.makeReservation:
                startMakeReservation();
                return true;
            case R.id.deleteReservation:
                startDeleteReservation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startDeleteReservation() {
        Intent myIntent = new Intent(Home.this, DeleteReservation.class);
        Home.this.startActivity(myIntent);
    }

    private void startMakeReservation() {
    }

    private void startViewProfile() {

    }

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