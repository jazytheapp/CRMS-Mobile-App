package ca.gbc.comp3074.crms;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class ViewProfile extends AppCompatActivity {
    private  FirebaseStorage storage;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private Button editProfile_button;
    private Button changePassword_button;
    private TextView name_view;
    private TextView email_view;
    private ImageView profile_view;
    private FileDownloadTask image_uri;
    DocumentReference docRef = db.collection("users").document(user.getUid());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        storage= FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("profileImages");
        StorageReference profileRef = storageReference.child(user.getUid()+ ".jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profile_view);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                profile_view.setImageResource(R.drawable.profile);
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        name_view = (TextView) findViewById(R.id.textView_name);
        email_view = (TextView) findViewById(R.id.textView_email);
        profile_view = (ImageView) findViewById(R.id.imageView_viewProfile);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    String name = document.getString("fullName");
                    name_view.setText(name);
                    email_view.setText(user.getEmail());
                }
            }
        });
        //profile_view.setImageURI(currentUser.getPhotoUrl());
        editProfile_button = (Button) findViewById(R.id.button_editProfile);
        editProfile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditProfile();
            }
        });
        changePassword_button = (Button) findViewById(R.id.button_changePassword);
        changePassword_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openchangePassword();
            }
        });
    }

    public void openEditProfile(){
        Intent intent = new Intent(this, EditProfile.class);
        startActivity(intent);
    }
    public void openchangePassword(){
        Intent intent = new Intent(this, ChangePassword.class);
        startActivity(intent);
    }
}