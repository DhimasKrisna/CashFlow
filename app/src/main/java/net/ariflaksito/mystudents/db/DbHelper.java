package net.ariflaksito.mystudents.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.ariflaksito.mystudents.model.Chasflow;
;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbchasflow";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_STD = "chasflow";
    private static final String KEY_ID = "id";
    private static final String KEY_NOMINAL = "nominal";
    private static final String KEY_KETERANGAN = "keterangan";
    private static final String KEY_TANGGAL = "tanggal";
    private static final String KEY_KELUAR = "keluar";


    private static final String CREATE_TABLE_CHASFLOW = "CREATE TABLE "
            + TABLE_STD + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NOMINAL+ " TEXT, " + KEY_KETERANGAN + " TEXT, "+ KEY_TANGGAL+ " TEXT, "+ KEY_KELUAR+ " TEXT );";

    public DbHelper( Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CHASFLOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_STD + "'");
        onCreate(sqLiteDatabase);
    }


    //    create insert data
    public long addChasflow(String nominal, String keterangan, String tanggal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOMINAL, nominal);
        values.put(KEY_KETERANGAN, keterangan);
        values.put(KEY_TANGGAL, tanggal);
        long insert = db.insert(TABLE_STD, null, values);

        return insert;
    }

    public long addKeluarChasflow ( String keterangan, String tanggal ,String keluar) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_KETERANGAN, keterangan);
        values.put(KEY_TANGGAL, tanggal);
        values.put(KEY_KELUAR, keluar);
        long insert = db.insert(TABLE_STD, null, values);

        return insert;
    }

    public ArrayList<Chasflow> getAllChasflow() {
        ArrayList<Chasflow> userModelArrayList = new ArrayList<Chasflow>();

        String selectQuery = "SELECT * FROM " + TABLE_STD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Chasflow std = new Chasflow();
                std.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                std.setNominal(c.getString(c.getColumnIndex(KEY_NOMINAL)));
                std.setKeluar(c.getString(c.getColumnIndex(KEY_KELUAR)));
                std.setKeterangan(c.getString(c.getColumnIndex(KEY_KETERANGAN)));
                std.setTanggal(c.getString(c.getColumnIndex(KEY_TANGGAL)));
                // adding to Students list
                userModelArrayList.add(std);
            } while (c.moveToNext());
        }
        return userModelArrayList;
    }

    public ArrayList<Chasflow> getAllKeluarChasflow() {
        ArrayList<Chasflow> userModelArrayList = new ArrayList<Chasflow>();

        String selectQuery = "SELECT * FROM " + TABLE_STD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Chasflow std = new Chasflow();
                std.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                std.setKeluar(c.getString(c.getColumnIndex(KEY_KELUAR)));
                std.setKeterangan(c.getString(c.getColumnIndex(KEY_KETERANGAN)));
                std.setTanggal(c.getString(c.getColumnIndex(KEY_TANGGAL)));
                // adding to Students list
                userModelArrayList.add(std);
            } while (c.moveToNext());
        }
        return userModelArrayList;
    }


    public Cursor Graph(String id, String nominal){
        String[] columns = { KEY_ID, KEY_NOMINAL };
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_STD,columns,null,null,null,null,null);
        return cursor;

    }



    public void deleteChasflow(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STD, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }



    public int updateChasflow(int id, String nominal, String keterangan, String tanggal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOMINAL, nominal);
        values.put(KEY_KETERANGAN, keterangan);
        values.put(KEY_TANGGAL, tanggal);

        return db.update(TABLE_STD, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});

    }

    public int updateKeluarChasflow(int id, String keluar, String keterangan, String tanggal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_KELUAR, keluar);
        values.put(KEY_KETERANGAN, keterangan);
        values.put(KEY_TANGGAL, tanggal);

        return db.update(TABLE_STD, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});

    }

    public String getCount() {

//        initialize database
        SQLiteDatabase database = getReadableDatabase();
//        string
        String sQty;
//        query for getting price sum
        String sQuery = "select sum(nominal) from "+ TABLE_STD;
        Cursor cursor = database.rawQuery(sQuery, null);
//        condition
        if(cursor.moveToFirst()) {
            sQty = String.valueOf(cursor.getInt(0));

        }else {
            sQty="0";
        }
        cursor.close();
        database.close();
//        pass total nominal
        return sQty;
    }

    public String getSum() {
//        initialize database
        SQLiteDatabase database = getReadableDatabase();
//        string
        String Amount;
//        query for getting price sum
        String sQuery = "select sum(nominal) from "+ TABLE_STD;
        Cursor cursor = database.rawQuery(sQuery, null);
//        condition
        if(cursor.moveToFirst()) {
            Amount = String.valueOf(cursor.getInt(0));

        }else {
            Amount="0";
        }
        cursor.close();
        database.close();
//        pass total nominal
        return Amount;

    }

    public String getKeluarSum() {
//        initialize database
        SQLiteDatabase database = getReadableDatabase();
//        string
        String Amount;
//        query for getting price sum
        String sQuery = "select sum(keluar) from "+ TABLE_STD;
        Cursor cursor = database.rawQuery(sQuery, null);
//        condition
        if(cursor.moveToFirst()) {
            Amount = String.valueOf(cursor.getInt(0));

        }else {
            Amount="0";
        }
        cursor.close();
        database.close();
//        pass total nominal
        return Amount;

    }

    

}