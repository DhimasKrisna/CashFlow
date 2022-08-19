package net.ariflaksito.mystudents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import net.ariflaksito.mystudents.db.DbHelper;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final String COL_1 ="id";
    public static final String COL_2 ="nominal";

    SQLiteDatabase sqLiteDatabase;
    DbHelper dbHelper;
    private EditText etName, etNim, etTanggal;
    private Button btnSave, btnList , btnKeluar;
//    LineGraphSeries<DataPoint> series;
//    GraphView gp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);

        etName = findViewById(R.id.edt_name);
        etNim = findViewById(R.id.edt_nim);
        etTanggal = findViewById(R.id.edt_tanggal);
        btnSave = findViewById(R.id.btn_submit);
        btnKeluar = findViewById(R.id.btn_back);
        btnList = findViewById(R.id.btn_list);
//        gp = findViewById(R.id.graph);

        sqLiteDatabase = dbHelper.getReadableDatabase();
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
                    Toast.makeText(MainActivity.this, "Error: Nominal harus diisi!", Toast.LENGTH_SHORT).show();
                } else if (etName.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Error: Keterangan harus diisi!", Toast.LENGTH_SHORT).show();
                } else if (etTanggal.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Error: Tanggal harus diisi!", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.addChasflow(etNim.getText().toString(), etName.getText().toString(), etTanggal.getText().toString());
//                    series = new LineGraphSeries<DataPoint>(getdata());
//                    gp.addSeries(series);
                    etName.setText("");
                    etNim.setText("");
                    etTanggal.setText("");
                    Toast.makeText(MainActivity.this, "Simpan berhasil!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListStudentsActivity.class);
                startActivity(intent);
            }
        });

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BerandaActivity.class);
                startActivity(intent);
            }
        });

    }

//    private DataPoint[] getdata() {
//
//        String[] columns = {COL_1,COL_2 };
//        Cursor cursor = sqLiteDatabase.query("chasflow",columns,null,null,null,null,null);
//
//        DataPoint[] dp=new DataPoint[cursor.getCount()];
//        for (int i=0; i<cursor.getCount(); i++) {
//            cursor.moveToNext();
//            dp[i] = new DataPoint(cursor.getInt(0),cursor.getInt(1));
//        }
//    return dp;
//    }


}