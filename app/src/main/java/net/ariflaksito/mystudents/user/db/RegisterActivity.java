package net.ariflaksito.mystudents.user.db;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import net.ariflaksito.mystudents.R;

public class RegisterActivity extends AppCompatActivity {
    dbHelperUser db;
    private EditText Email;
    private EditText Password;
    private EditText passwordconf;
    private Button Login;
    private Button Register;
    private CheckBox showpass;
    private CheckBox showpasss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new dbHelperUser(this);
        Email = findViewById(R.id.edEmail);
        Password = findViewById(R.id.edPass);
        passwordconf = findViewById(R.id.edPassConf);
        showpass = findViewById(R.id.showpass);
        showpasss =findViewById(R.id.showpasss);

        Login = findViewById(R.id.loginnbtn);
        Register = findViewById(R.id.registernbtn);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        showpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showpasss.isChecked()) {

                    //Saat password dalam keadaan checked , maka password akan ditampilkan
                    Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //jika tidak, maka password akan disembunyikan
                    Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        showpasss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showpass.isChecked()) {

                    //Saat password dalam keadaan checked , maka password akan ditampilkan
                    passwordconf.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //jika tidak, maka password akan disembunyikan
                    passwordconf.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();
                String passwordconfs = passwordconf.getText().toString().trim();


                if (password.equals(passwordconfs)){
                    long val = db.addUser(email,password);
                    if(val > 0) {
                        Toast.makeText(RegisterActivity.this, "kamu resgister", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "kamu Registeractivity Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this,"Password is not matching",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
