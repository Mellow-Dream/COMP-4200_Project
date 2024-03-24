package com.example.group_project;

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

    public Cursor displayAthleteData() {
        // This will display all athlete data from the database
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + athleteTable, null);

        return cursor;
    }

    public long editData(String inputTitle, String descriptionInput) {

        return 0;
    }

    public int delete(String titleInput) {

        return 0;
    }
}