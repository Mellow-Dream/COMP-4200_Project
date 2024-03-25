package com.example.comp_4200project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    String databaseName = "bioinformatics";
    String athleteTable = "athlete", bodpodTable = "bodpod", wingateTable = "wingate", biodexTable = "biodex";

    public DBHelper(@Nullable Context context, @Nullable String name,
                    @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /* Creating the DB involves 4 tables in total.
     * 1. Athletes  (studentID, studentName, dateOfBirth, faculty, height, weight, team, jerseyNumber)
     * 2. Bodpod    (TEE, REE, BodyFat, FatFree)
     * 3. Wingate   (MinPower, PeakPower, AvgPower)
     * 4. Biodex    (LeftQuadMax, RightQuadMax, LeftHamMax, RightHamMax)    */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Start with the main Athlete table
        // String q = "CREATE TABLE course(_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT)";
        String query = "CREATE TABLE athlete(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "studentID TEXT, studentName TEXT, dateOfBirth TEXT, faculty TEXT, "
                + "height REAL, weight REAL, team TEXT, jerseyNumber INTEGER)";
        db.execSQL(query);

        // Second Bodpod table
        query = "CREATE TABLE bodpod(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "tee INTEGER, ree INTEGER, body_fat REAL, fat_free REAL)";
        db.execSQL(query);

        // Third Wingate table
        query = "CREATE TABLE wingate(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "min_power REAL, peak_power REAL, avg_power REAL)";
        db.execSQL(query);

        // Fourth Biodex table
        query = "CREATE TABLE biodex(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "l_quad_max REAL, r_quad_max REAL, l_ham_max REAL, r_ham_max REAL)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public long addNewAthlete(String studentID, String studentName, String dob, String faculty,
                              Float height, Float weight, String team, int jerseyNumber) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Add the values for Athlete table
        contentValues.put("studentID", studentID);
        contentValues.put("studentName", studentName);
        contentValues.put("dateOfBirth", dob);
        contentValues.put("faculty", faculty);
        contentValues.put("height", height);
        contentValues.put("weight", weight);
        contentValues.put("team", team);
        contentValues.put("jerseyNumber", jerseyNumber);

        // Returns row number on success, negative otherwise
        return db.insert(athleteTable, null, contentValues);
    }

    public long addBiodexData(String StudentID, Float lqm, Float rqm, Float lhm, Float rhm){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("_id", StudentID);
        contentValues.put("l_quad_max", lqm);
        contentValues.put("r_quad_max", rqm);
        contentValues.put("l_ham_max", lhm);
        contentValues.put("r_ham_max", rhm);

        return db.insert(biodexTable, null, contentValues);
    }
    public long addBodpodData(String StudentID, int tee, int ree, Float bf, Float ff){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("_id", StudentID);
        contentValues.put("tee", tee);
        contentValues.put("ree", ree);
        contentValues.put("body_fat", bf);
        contentValues.put("fat_free", ff);

        return db.insert(bodpodTable, null, contentValues);
    }
    public long addWingateData(String StudentID, Float mp, Float pp, Float ap){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("_id", StudentID);
        contentValues.put("min_power", mp);
        contentValues.put("peak_power", pp);
        contentValues.put("avg_power", ap);

        return db.insert(wingateTable, null, contentValues);
    }

    public Cursor displayAthleteData() {
        // This will display all athlete data from the database
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + athleteTable, null);

        return cursor;
    }
    public Cursor displayBiodexData() {
        // This will display all athlete data from the database
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + biodexTable, null);

        return cursor;
    }
    public Cursor displayBodpodData() {
        // This will display all athlete data from the database
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + bodpodTable, null);

        return cursor;
    }
    public Cursor displayWingateData() {
        // This will display all athlete data from the database
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + wingateTable, null);

        return cursor;
    }

    public long editData(String inputTitle, String descriptionInput) {

        return 0;
    }

    public int delete(String titleInput) {

        return 0;
    }
}
