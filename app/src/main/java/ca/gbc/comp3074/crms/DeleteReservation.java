package ca.gbc.comp3074.crms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_reservation);
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

                        ListView listView = findViewById(R.id.delete_reservation_listview);
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


;
    }
}