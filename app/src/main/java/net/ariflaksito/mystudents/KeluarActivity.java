package net.ariflaksito.mystudents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.ariflaksito.mystudents.db.DbHelper;

import java.util.Calendar;

public class KeluarActivity extends AppCompatActivity {
    DbHelper dbHelper;
    private EditText etName, etNim, etTanggal;
    private Button btnSave, btnList, btnKeluar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keluar);

        dbHelper = new DbHelper(this);

        etName = findViewById(R.id.edt_name);
        etNim = findViewById(R.id.edt_nim);
        etTanggal = findViewById(R.id.edt_tanggal);
        btnSave = findViewById(R.id.btn_submit);
        btnList = findViewById(R.id.btn_list);
        btnKeluar = findViewById(R.id.btn_back);

//        tanggal format
        etTanggal.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        if (mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon - 1);

                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    etTanggal.setText(current);
                    etTanggal.setSelection(sel < current.length() ? sel : current.length());


                }
            }



            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNim.getText().toString().isEmpty()) {
                    Toast.makeText(KeluarActivity.this, "Error: Nominal harus diisi!", Toast.LENGTH_SHORT).show();
                } else if (etName.getText().toString().isEmpty()) {
                    Toast.makeText(KeluarActivity.this, "Error: Keterangan harus diisi!", Toast.LENGTH_SHORT).show();
                } else if (etTanggal.getText().toString().isEmpty()) {
                    Toast.makeText(KeluarActivity.this, "Error: Tanggal harus diisi!", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.addKeluarChasflow(etName.getText().toString(), etTanggal.getText().toString(), etNim.getText().toString());
                    etName.setText("");
                    etNim.setText("");
                    etTanggal.setText("");
                    Toast.makeText(KeluarActivity.this, "Simpan berhasil!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KeluarActivity.this, ListStudentsActivity.class);
                startActivity(intent);
            }
        });


        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KeluarActivity.this, BerandaActivity.class);
                startActivity(intent);
            }
        });
    }

}