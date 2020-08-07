package com.example.ApkPKL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper  extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "user.db";
    public static final String TABLE_NAME = "user_table";
    public static final String TABLE_RIWAYAT = "user_ri_table";

    public static final String COL_NAME_USERNAME = "USERNAME";
    public static final String COL_NAME_PASSWORD = "PASSWORD";
    public static final String COL_NAME_EMAIL = "EMAIL";
    public static final String COL_NAME_JENISKELAMIN = "JENIS_KELAMIN";
    public static final String COL_RIWAYAT_USERNAME = "USERNAME";
    public static final String COL_RIWAYAT_TANGGAL = "TANGGAL";
    public static final String COL_RIWAYAT_NAMA = "NAMA";
    public static final String COL_RIWAYAT_KONTAK = "KONTAK";
    public static final String COL_RIWAYAT_ALAMAT = "ALAMAT";


    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COL_NAME_USERNAME + " TEXT PRIMARY KEY," + COL_NAME_EMAIL + " TEXT,"
            + COL_NAME_PASSWORD + " TEXT," + COL_NAME_JENISKELAMIN + " TEXT" + ")";

    private String CREATE_USER_PASIEN = "CREATE TABLE " + TABLE_RIWAYAT + "("
            + COL_RIWAYAT_USERNAME + " TEXT," + COL_RIWAYAT_TANGGAL + " TEXT,"
            + COL_RIWAYAT_NAMA + " TEXT," + COL_RIWAYAT_KONTAK + " TEXT,"
            + COL_RIWAYAT_ALAMAT + " TEXT" + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private String DROP_USER_PASIEN = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_USER_PASIEN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_USER_PASIEN);
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME_USERNAME, user.getUsername());
        values.put(COL_NAME_EMAIL, user.getEmail());
        values.put(COL_NAME_PASSWORD, user.getPassword());
        values.put(COL_NAME_JENISKELAMIN, user.getJenisKelamin());

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        //db.close();
    }

    public void addRiwayat(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_RIWAYAT_USERNAME, user.getUsername());
        values.put(COL_RIWAYAT_TANGGAL, user.getTanggal());
        values.put(COL_RIWAYAT_NAMA, user.getNama());
        values.put(COL_RIWAYAT_KONTAK, user.getKontak());
        values.put(COL_RIWAYAT_ALAMAT, user.getAlamat());

        // Inserting Row
        db.insert(TABLE_RIWAYAT, null, values);
        //db.close();
    }

    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COL_NAME_USERNAME,
                COL_NAME_EMAIL,
                COL_NAME_PASSWORD,
                COL_NAME_JENISKELAMIN
        };
        // sorting orders
        String sortOrder =
                COL_NAME_USERNAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUsername(cursor.getString(cursor.getColumnIndex(COL_NAME_USERNAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COL_NAME_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COL_NAME_PASSWORD)));
                user.setJenisKelamin(cursor.getString(cursor.getColumnIndex(COL_NAME_JENISKELAMIN)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        // return user list
        return userList;
    }

    public List<User> getAllRiwayat(String username) {
        String[] columns = {
                COL_RIWAYAT_USERNAME,
                COL_RIWAYAT_TANGGAL,
                COL_RIWAYAT_NAMA,
                COL_RIWAYAT_KONTAK,
                COL_RIWAYAT_ALAMAT
        };

        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COL_RIWAYAT_USERNAME + " = ?";

        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_RIWAYAT,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUsername(cursor.getString(cursor.getColumnIndex(COL_RIWAYAT_USERNAME)));
                user.setTanggal(cursor.getString(cursor.getColumnIndex(COL_RIWAYAT_TANGGAL)));
                user.setNama(cursor.getString(cursor.getColumnIndex(COL_RIWAYAT_NAMA)));
                user.setKontak(cursor.getString(cursor.getColumnIndex(COL_RIWAYAT_KONTAK)));
                user.setAlamat(cursor.getString(cursor.getColumnIndex(COL_RIWAYAT_ALAMAT)));

                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        return userList;
    }

    public boolean checkUser(String username) {

        String[] columns = {
                COL_NAME_USERNAME
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COL_NAME_USERNAME + " = ?";

        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        //db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public boolean checkUser(String username, String password) {

        String[] columns = {
                COL_NAME_EMAIL
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COL_NAME_USERNAME + " = ?" + " AND " + COL_NAME_PASSWORD + " = ?";

        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();

        cursor.close();
        //db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public String getJenisKelamin(String username) {
        User user = new User();

        String[] columns = {
                COL_NAME_JENISKELAMIN
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COL_NAME_USERNAME + " = ?";

        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            do {
                user.setJenisKelamin(cursor.getString(cursor.getColumnIndex(COL_NAME_JENISKELAMIN)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        return user.getJenisKelamin();
    }

    public String getNama(String username) {
        User user = new User();

        String[] columns = {
                COL_RIWAYAT_NAMA
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COL_RIWAYAT_USERNAME + " = ?";

        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_RIWAYAT,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            do {
                user.setNama(cursor.getString(cursor.getColumnIndex(COL_RIWAYAT_NAMA)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        return user.getNama();
    }

    public String getTanggal(String username) {
        User user = new User();

        String[] columns = {
                COL_RIWAYAT_TANGGAL
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COL_RIWAYAT_USERNAME + " = ?";

        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_RIWAYAT,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            do {
                user.setTanggal(cursor.getString(cursor.getColumnIndex(COL_RIWAYAT_TANGGAL)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        return user.getTanggal();
    }

    public String getKontak(String username) {
        User user = new User();

        String[] columns = {
                COL_RIWAYAT_KONTAK
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COL_RIWAYAT_USERNAME + " = ?";

        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_RIWAYAT,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            do {
                user.setKontak(cursor.getString(cursor.getColumnIndex(COL_RIWAYAT_KONTAK)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        return user.getKontak();
    }

    public String getAlamat(String username) {
        User user = new User();

        String[] columns = {
                COL_RIWAYAT_ALAMAT
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COL_RIWAYAT_USERNAME + " = ?";

        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_RIWAYAT,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            do {
                user.setAlamat(cursor.getString(cursor.getColumnIndex(COL_RIWAYAT_ALAMAT)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        return user.getAlamat();
    }

    public String getEmail(String username) {
        User user = new User();

        String[] columns = {
                COL_NAME_EMAIL
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COL_NAME_USERNAME + " = ?";

        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            do {
                user.setEmail(cursor.getString(cursor.getColumnIndex(COL_NAME_EMAIL)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        return user.getEmail();
    }

    public String getName(String username) {
        User user = new User();

        String[] columns = {
                COL_NAME_USERNAME
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COL_NAME_USERNAME + " = ?";

        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_RIWAYAT,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            do {
                user.setUsername(cursor.getString(cursor.getColumnIndex(COL_NAME_USERNAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close();

        return user.getUsername();
    }
}