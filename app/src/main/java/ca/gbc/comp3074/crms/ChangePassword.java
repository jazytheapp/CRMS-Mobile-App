package ca.gbc.comp3074.crms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {
    EditText password;
    EditText confirm_password;
    private Button update_password;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        update_password = (Button) findViewById(R.id.button_updatePassword);
        password = (EditText) findViewById(R.id.editText_newPassword);
        confirm_password = (EditText) findViewById(R.id.editText_confirmNewPassword);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user  = auth.getCurrentUser();
        update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().toString().equals(confirm_password.getText().toString())){
                    String new_password = password.getText().toString();
                    user.updatePassword(new_password);
                    openProfile();
                    Toast.makeText(ChangePassword.this, "Password Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
    public void openProfile(){
        Intent intent = new Intent(this, ViewProfile.class);
        startActivity(intent);
    }
}