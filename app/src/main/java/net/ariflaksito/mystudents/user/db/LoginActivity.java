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

import net.ariflaksito.mystudents.BerandaActivity;
import net.ariflaksito.mystudents.R;

import static android.text.TextUtils.isEmpty;

public class LoginActivity  extends AppCompatActivity{

    private final AppCompatActivity activity = LoginActivity.this;

    dbHelperUser db;
    private EditText Email;
    private EditText Password;
    private CheckBox showpassss;

    private TextView Register, Loginuser;
    private Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new dbHelperUser(this);
        Email = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        showpassss = findViewById(R.id.showpass);

        Loginuser = findViewById(R.id.signin);


        Login = findViewById(R.id.loginbtn);
//        Register = findViewById(R.id.register);


        showpassss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showpassss.isChecked()) {

                    //Saat password dalam keadaan checked , maka password akan ditampilkan
                    Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //jika tidak, maka password akan disembunyikan
                    Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

//        Register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();
                if (!isEmpty(Email.getText().toString())
                        && !isEmpty(Password.getText().toString())) {
                    Boolean res = db.checkUser(email, password);
                    if (res == true) {
                        Intent intent = new Intent(LoginActivity.this, BerandaActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "sukses login ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong password , email", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login gagal silakan isi lagi", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        Loginuser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, Login_User.class);
//                startActivity(intent);
//                finish();
//            }
//        });

    }
}
