package ca.gbc.comp3074.crms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DeleteReservation extends AppCompatActivity {

    String[] mobileArray = {"Reservation 1","Reservation 2","Reservation 3","Reservation 4",
            "Reservation 5","Reservation 6","Reservation 7","Reservation 8"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_reservation);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.delete_reservation_layout, R.id.label, mobileArray);

        ListView listView = findViewById(R.id.delete_reservation_listview);
        listView.setAdapter(adapter);
    }
}