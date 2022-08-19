package net.ariflaksito.mystudents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import net.ariflaksito.mystudents.user.db.LoginActivity;
import net.ariflaksito.mystudents.user.db.RegisterActivity;
import net.ariflaksito.mystudents.user.db.dbHelperUser;

public class SettingActivity extends AppCompatActivity {
EditText Lupapassword , password1 ;
TextView status;
Button Simpan, keluar;
dbHelperUser db;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Lupapassword = findViewById(R.id.pass1);
        status = findViewById(R.id.status);
        password1 = findViewById(R.id.pass2);
        Simpan = findViewById(R.id.submitbtn);
        keluar = findViewById(R.id.kembalibtn);

        Intent intent = getIntent();
        email = intent.getStringExtra("EMAIL");
        db = new dbHelperUser(this);

        Lupapassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePassword();
            }
        });

        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            updatePassword();
            emptyInputEditText();
            Toast.makeText(getApplicationContext(), "Success di simpan password baru", Toast.LENGTH_SHORT).show();
            }
        });

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, BerandaActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void updatePassword() {

        String value1 = Lupapassword.getText().toString().trim();
        String value2 = password1.getText().toString().trim();

        if (value1.isEmpty() && value2.isEmpty()){
            Toast.makeText(this, "fill all fields ", Toast.LENGTH_LONG).show();
            return;
        }

        if (!value1.contentEquals(value2)){
            Toast.makeText(this, "password doesn't match", Toast.LENGTH_LONG).show();
            return;
        }

        if (!db.checkUsers(email)) {

            Snackbar.make(status, "email doesn't exist", Snackbar.LENGTH_LONG).show();
            return;

        } else {
            db.updatePassword(email, value1);

            Toast.makeText(this, "password reset successfully", Toast.LENGTH_SHORT).show();
            emptyInputEditText();

            Intent intent = new Intent(this, BerandaActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void emptyInputEditText()
    {
        Lupapassword.setText("");
        password1.setText("");
    }


}
