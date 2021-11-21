package facci.com.conversorac;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class XMLHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "currency.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CURRENCY = "currency";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_CURRENCY = "currency";
    private static final String COLUMN_VALUE = "value";

    public XMLHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CURRENCY_TABLE = "CREATE TABLE " + TABLE_CURRENCY + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_CURRENCY + " TEXT,"
                + COLUMN_VALUE + " REAL" + ")";
        db.execSQL(CREATE_CURRENCY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENCY);
        onCreate(db);
    }

    public static void addCurrency(String currency, double value) {
        SQLiteDatabase db = this.getWritableDatabase();
        String INSERT_CURRENCY = "INSERT INTO " + TABLE_CURRENCY + "(" + COLUMN_CURRENCY + "," + COLUMN_VALUE + ") VALUES ('" + currency + "'," + value + ")";
        db.execSQL(INSERT_CURRENCY);
        db.close();
    }

    public List<String> getAllCurrencies() {
        List<String> currencies = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_CURRENCY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                currencies.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return currencies;
    }

    public double getCurrencyValue(String currency) {
        double value = 0;
        String selectQuery = "SELECT  * FROM " + TABLE_CURRENCY + " WHERE " + COLUMN_CURRENCY + " = '" + currency + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                value = cursor.getDouble(2);
            } while (cursor.moveToNext());
        }
        return value;
    }

    public void updateCurrencyValue(String currency, double value) {
        SQLiteDatabase db = this.getWritableDatabase();
        String UPDATE_CURRENCY = "UPDATE " + TABLE_CURRENCY + " SET " + COLUMN_VALUE + " = " + value + " WHERE " + COLUMN_CURRENCY + " = '" + currency + "'";
        db.execSQL(UPDATE_CURRENCY);
        db.close();
    }

    public void deleteCurrency(String currency) {
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE_CURRENCY = "DELETE FROM " + TABLE_CURRENCY + " WHERE " + COLUMN_CURRENCY + " = '" + currency + "'";
        db.execSQL(DELETE_CURRENCY);
        db.close();
    }
}