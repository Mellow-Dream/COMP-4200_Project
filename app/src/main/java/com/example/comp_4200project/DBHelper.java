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

        /* Creating the DB involves 4 tables in total:
         * 1. Athletes  (studentID, studentName, dateOfBirth, faculty, height, weight, team, jerseyNumber)
         * 2. Bodpod    (studentID, TEE, REE, BodyFat, FatFree)
         * 3. Wingate   (studentID, MinPower, PeakPower, AvgPower)
         * 4. Biodex    (studentID, LeftQuadMax, RightQuadMax, LeftHamMax, RightHamMax)    */
        @Override
        public void onCreate(SQLiteDatabase db) {
            // Start with the main Athlete table
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
            // Not sure if needed
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
        }

        /* Methods for adding data to tables */
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

        public long addNewBodpodTest(String studentID, int tee, int ree, float body_fat, float fat_free) {
            // fat_free should be (100% - body_fat)
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            // Add data for the Bodpod table
            contentValues.put("_id", studentID);
            contentValues.put("tee", tee);
            contentValues.put("ree", ree);
            contentValues.put("body_fat", body_fat);
            contentValues.put("fat_free", fat_free);

            // Returns row number on success, negative otherwise
            return db.insert(bodpodTable, null, contentValues);
        }

        public long addNewWingateTest(String studentID, float min_power, float peak_power, float avg_power) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            // Add data for the Wingate table
            contentValues.put("_id", studentID);
            contentValues.put("min_power", min_power);
            contentValues.put("peak_power", peak_power);
            contentValues.put("avg_power", avg_power);

            // Returns row number on success, negative otherwise
            return db.insert(wingateTable, null, contentValues);
        }

        public long addNewBiodexTest(String studentID, float right_quad_max, float left_quad_max,
                                     float right_ham_max, float left_ham_max) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            // Add data for the Biodex table
            contentValues.put("_id", studentID);
            contentValues.put("left_quad_max", left_quad_max);
            contentValues.put("right_quad_max", right_quad_max);
            contentValues.put("left_ham_max", left_ham_max);
            contentValues.put("right_ham_max", right_ham_max);

            // Returns row number on success, negative otherwise
            return db.insert(biodexTable, null, contentValues);
        }

        /* Methods for retrieving all data from a table for display (Coaches) */
        public Cursor displayAllAthleteData() {
            // This will display all athlete data from the database
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + athleteTable, null);

            return cursor;
        }

        public Cursor displayAllBodpodData() {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + bodpodTable, null);

            return cursor;
        }

        public Cursor displayAllWingateData() {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + wingateTable, null);

            return cursor;
        }

        public Cursor displayAllBiodexData() {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + biodexTable, null);

            return cursor;
        }

        /* Methods for retrieving select data from tables for display (Players) */
        public Cursor displayBodpodTest(String studentID) {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + bodpodTable + " WHERE studentID=?", new String[]{studentID});

            return cursor;
        }

        public Cursor displayWingateTest(String studentID) {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + wingateTable + " WHERE studentID=?", new String[]{studentID});

            return cursor;
        }

        public Cursor displayBiodexTest(String studentID) {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + biodexTable + " WHERE studentID=?", new String[]{studentID});

            return cursor;
        }

        public long editData(String inputTitle, String descriptionInput) {

            return 0;
        }

        public int delete(String titleInput) {

            return 0;
        }
    }