package ca.gbc.comp3074.crms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeleteReservation extends AppCompatActivity {

    String[] mobileArray = {"Reservation 1","Reservation 2","Reservation 3","Reservation 4",
            "Reservation 5","Reservation 6","Reservation 7","Reservation 8"};

    List<String> displayRestaurants = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_reservation);
        listView = findViewById(R.id.delete_reservation_listview);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(FirebaseAuth.getInstance().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        HashMap<String, String> alist = (HashMap<String, String>) document.get("reservations");
                        alist.forEach((key, value) -> {
                            displayRestaurants.add(key + "-" + value);
                        });

                        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.delete_reservation_layout, R.id.label, displayRestaurants);
                        listView.setAdapter(adapter);


                    }
                    else
                        Log.d("SABIH2", "EMPTY");
                }
                else
                    Log.d("SABIH3", "FAILED");
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();
            }
        });

    }
}