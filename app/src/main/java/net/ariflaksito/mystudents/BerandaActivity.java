package net.ariflaksito.mystudents;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import net.ariflaksito.mystudents.db.DbHelper;
import net.ariflaksito.mystudents.model.Chasflow;

public class BerandaActivity extends AppCompatActivity {
    String COL_1 ="id";
    String COL_2 ="nominal";

    LineGraphSeries<DataPoint> series;

    TextView angka1 , angka2;
    ImageView btn1 , btn2 , btn3, btn4;
//    GraphView gp;
    SQLiteDatabase sqLiteDatabase;
    private DbHelper dbHelper;
//    private Chasflow student;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);
        angka1 = findViewById(R.id.pemasukanid);
        angka2 = findViewById(R.id.pengeluaranid);

        btn1 = findViewById(R.id.tambahpemasukan);
        btn2 = findViewById(R.id.tambahpengeluaran);
        btn3 = findViewById(R.id.detailcash);
        btn4 = findViewById(R.id.pengaturan);

//        gp = findViewById(R.id.graph);

//       series = new LineGraphSeries<>(getdata());
//       gp.addSeries(series);

        dbHelper = new DbHelper(this);
        sqLiteDatabase = dbHelper.getReadableDatabase();

        angka1.setText(String.format("Rp. %s",dbHelper.getSum() ));
        angka2.setText(String.format("Rp. %s",dbHelper.getKeluarSum() ));



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BerandaActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BerandaActivity.this, KeluarActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BerandaActivity.this, ListStudentsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BerandaActivity.this, SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


//    private DataPoint[] getdata() {
//
//      DataPoint[] dp  = new DataPoint[]{
//              new DataPoint(0,1),
//              new DataPoint(2,5),
//              new DataPoint(4,2),
//              new DataPoint(6,4),
//
//      };
//        return dp;
//    }
//
//    private DataPoint[] getdata() {
//
//        String[] columns = {COL_1, COL_2};
//        Cursor cursor = sqLiteDatabase.query("chasflow",columns,null,null,null,null,null);
//
//        DataPoint[] dp=new DataPoint[cursor.getCount()];
//        for (int i=0; i<cursor.getCount(); i++) {
//            cursor.moveToNext();
//            dp[i] = new DataPoint(cursor.getInt(0),cursor.getInt(1));
//        }
//        return dp;
//    }

}
