package ca.gbc.comp3074.crms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.IResultReceiver;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.gbc.comp3074.crms.models.Restaurant;

public class Home extends AppCompatActivity {

    Button logoutBtn;
    TextView currentUserText;

    ListView myListView;
    List<Restaurant> restaurantList;

    DatabaseReference restaurantRef;
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);

        MenuItem item = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processSearch(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processSearch(s);
                return true;
            }
        });
        return true;
    }

    private void processSearch(String s){
        myListView = findViewById(R.id.myListView);
        restaurantList = new ArrayList<>();

        FirebaseFirestore resRef = FirebaseFirestore.getInstance();
        CollectionReference questionsRef = resRef.collection("restaurants");
        questionsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        Restaurant restaurant = document.toObject(Restaurant.class);

                        if(restaurant.getName().toLowerCase().contains(s.toLowerCase())) {
                            restaurantList.add(restaurant);
                            Log.d("TAG", restaurant.getName());
                        }
                    }
                    ListAdapter adapter = new ListAdapter(Home.this, restaurantList);
                    myListView.setAdapter(adapter);

                }else{
                    Log.d("error","connection not working");
                }
            }
        });
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
            case R.id.search_view:
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
        Intent intent = new Intent(Home.this, MakeReservation.class);
        startActivity(intent);
    }

    private void startViewProfile() {
        Intent intent = new Intent(Home.this, ViewProfile.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logoutBtn = findViewById(R.id.logoutButton);
        currentUserText = findViewById(R.id.currentUserText);

        Intent intent = getIntent();
        FirebaseUser currentUser = (FirebaseUser) intent.getExtras().get("currentUser");

//        currentUserText.setText("Welcome: " + currentUser.getEmail() );

        logoutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent1 = new Intent(Home.this, LoginActivity.class);
            startActivity(intent1);
        });

        myListView = findViewById(R.id.myListView);
        restaurantList = new ArrayList<>();
        FirebaseFirestore resRef = FirebaseFirestore.getInstance();
        CollectionReference questionsRef = resRef.collection("restaurants");
        questionsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        Restaurant restaurant = document.toObject(Restaurant.class);
                        restaurantList.add(restaurant);
//                        Log.d("TAG", restaurant.getName());
                    }
                    ListAdapter adapter = new ListAdapter(Home.this, restaurantList);
                    myListView.setAdapter(adapter);

                }else{
                    Log.d("error","connection not working");
                }
            }
        });


    }
}
